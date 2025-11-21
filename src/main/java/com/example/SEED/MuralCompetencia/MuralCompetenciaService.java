package com.example.SEED.MuralCompetencia;

import com.example.SEED.ComboItem.ComboItemRepository;
import com.example.SEED.Preenchimento.Preenchimento;
import com.example.SEED.Preenchimento.PreenchimentoRepository;
import com.example.SEED.Perfil.NomePerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuralCompetenciaService {

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;

    @Autowired
    private ComboItemRepository comboItemRepository;

    public List<MuralCompetenciaDTO> getMuralPorCompetencia(Long competenciaId, String perfil) {

        List<Preenchimento> lista;

        // 👉 Sem filtro de perfil
        if (perfil == null || perfil.isBlank()) {
            lista = preenchimentoRepository
                    .findByCompetenciaIdOrderByDataPreenchimentoDesc(competenciaId);

        } else {
            // 👉 Converte String do front para enum NomePerfil
            NomePerfil perfilEnum = NomePerfil.valueOf(perfil);

            lista = preenchimentoRepository
                    .findByCompetenciaIdAndUsuarioPerfilNomePerfilOrderByDataPreenchimentoDesc(
                            competenciaId,
                            perfilEnum
                    );
        }

        // 👉 Monta o DTO final
        return lista.stream()
                .map(p -> {

                        var combos = comboItemRepository.findByItemId(p.getItem().getId());
                    String comboNome = combos.isEmpty()
                            ? null
                            : combos.get(0).getCombo().getNomeCombo();

                    return new MuralCompetenciaDTO(
                            p.getId(),
                            p.getCompetencia().getId(),
                            p.getCompetencia().getNome(),
                            p.getCompetencia().getDataFim(),

                            p.getUsuario().getNome(),
                            p.getUsuario().getPerfil().getNomePerfil().name(),

                            p.getEstruturaAdm().getName(),
                            null, // setor por enquanto

                            comboNome,
                            p.getItem().getNomeItem(),

                            p.getQuantidade(),
                            p.getValor(),
                            p.getDataPreenchimento()
                    );
                })
                .toList();
    }
}
