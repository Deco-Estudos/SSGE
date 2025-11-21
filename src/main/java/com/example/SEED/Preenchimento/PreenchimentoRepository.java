package com.example.SEED.Preenchimento;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Item.Item;
import com.example.SEED.Perfil.NomePerfil;
import com.example.SEED.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PreenchimentoRepository extends JpaRepository<Preenchimento, Long> {

    Optional<Preenchimento> findByEstruturaAdmAndCompetenciaAndItem(
            EstruturaAdm estruturaAdm,
            Competencia competencia,
            Item item
    );

    List<Preenchimento> findByEstruturaAdmAndCompetenciaAndUsuario(
            EstruturaAdm estruturaAdm,
            Competencia competencia,
            Usuario usuario
    );

    List<Preenchimento> findByCompetenciaIdOrderByDataPreenchimentoDesc(Long id);

    List<Preenchimento> findByCompetenciaIdAndUsuarioPerfilNomePerfilOrderByDataPreenchimentoDesc(
            Long competenciaId,
            NomePerfil nomePerfil
    );
}
