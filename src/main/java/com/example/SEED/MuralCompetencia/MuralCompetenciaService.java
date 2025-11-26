package com.example.SEED.MuralCompetencia;

import com.example.SEED.Censo.CensoRepository;
import com.example.SEED.ComboItem.ComboItemRepository;
import com.example.SEED.Preenchimento.Preenchimento;
import com.example.SEED.Preenchimento.PreenchimentoRepository;
import com.example.SEED.Perfil.NomePerfil;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MuralCompetenciaService {

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;

    @Autowired
    private ComboItemRepository comboItemRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private CensoRepository censoRepository;

    public List<Object> getMuralPorCompetencia(Long competenciaId, String perfil) {

        List<Preenchimento> lista;

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

        List<Object> mural = new ArrayList<>();

        // Preenchimentos
        for (Preenchimento p : lista) {

            var combos = comboItemRepository.findByItemId(p.getItem().getId());
            String comboNome = combos.isEmpty()
                    ? null
                    : combos.get(0).getCombo().getNomeCombo();

            List<Setor> setores = setorRepository.findByResponsaveisIdAndEstruturaAdmId(
                    p.getUsuario().getId(),
                    p.getEstruturaAdm().getId()
            );

            String setorNome = setores.isEmpty() ? null : setores.get(0).getNome();

            mural.add(new MuralCompetenciaDTO(
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
                    p.getDataPreenchimento(),
                    "PREENCHIMENTO"
            ));
        }

        // Censos
        var censos = censoRepository.findByCompetenciaId(competenciaId);

        for (var c : censos) {
            mural.add(new MuralCensoDTO(
                    c.getId(),
                    c.getCompetencia().getId(),
                    c.getCompetencia().getNome(),
                    c.getEstruturaAdm().getName(),
                    c.getUsuario().getNome(),
                    c.getUsuario().getPerfil().getNomePerfil().name(),
                    c.getQuantidadeAlunos(),
                    c.getDataPreenchimento(),
                    "CENSO"
            ));
        }

        return mural;
    }

}
