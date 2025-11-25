package com.example.SEED.MuralCompetencia;

import com.example.SEED.ComboItem.ComboItemRepository;
import com.example.SEED.Preenchimento.Preenchimento;
import com.example.SEED.Preenchimento.PreenchimentoRepository;
import com.example.SEED.Perfil.NomePerfil;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuralCompetenciaService {

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;

    @Autowired
    private ComboItemRepository comboItemRepository;

    @Autowired
    private SetorRepository setorRepository; // ⭐ ADICIONADO

    public List<MuralCompetenciaDTO> getMuralPorCompetencia(Long competenciaId, String perfil) {

        List<Preenchimento> lista;

        // Sem filtro
        if (perfil == null || perfil.isBlank()) {
            lista = preenchimentoRepository
                    .findByCompetenciaIdOrderByDataPreenchimentoDesc(competenciaId);

        } else {
            NomePerfil perfilEnum = NomePerfil.valueOf(perfil);

            lista = preenchimentoRepository
                    .findByCompetenciaIdAndUsuarioPerfilNomePerfilOrderByDataPreenchimentoDesc(
                            competenciaId,
                            perfilEnum
                    );
        }

        return lista.stream()
                .map(p -> {

                    // 👉 Combo
                    var combos = comboItemRepository.findByItemId(p.getItem().getId());
                    String comboNome = combos.isEmpty()
                            ? null
                            : combos.get(0).getCombo().getNomeCombo();

                    // 👉 BUSCAR SETOR (aqui acontece a mágica)
                    List<Setor> setores = setorRepository.findByResponsaveisIdAndEstruturaAdmId(
                            p.getUsuario().getId(),
                            p.getEstruturaAdm().getId()
                    );

                    String setorNome = setores.isEmpty() ? null : setores.get(0).getNome();

                    // 👉 Monta o DTO final do Mural
                    return new MuralCompetenciaDTO(
                            p.getId(),
                            p.getCompetencia().getId(),
                            p.getCompetencia().getNome(),
                            p.getCompetencia().getDataFim(),

                            p.getUsuario().getNome(),
                            p.getUsuario().getPerfil().getNomePerfil().name(),

                            p.getEstruturaAdm().getName(),
                            setorNome,

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
