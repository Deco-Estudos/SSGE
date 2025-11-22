package com.example.SEED.Preenchimento;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Item.Item;
import com.example.SEED.Perfil.NomePerfil;
import com.example.SEED.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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


    // SOMA DE MATERIAIS (Tudo que NÃO é RH)
    @Query("""
        SELECT SUM(p.valor * COALESCE(p.quantidade, 1)) 
        FROM Preenchimento p 
        WHERE (:estId IS NULL OR p.estruturaAdm.id = :estId) 
          AND (:compId IS NULL OR p.competencia.id = :compId)
          AND p.item.classificacao.nomeClassificacao <> 'Recursos Humanos'
    """)
    BigDecimal somarMateriais(@Param("estId") Long estruturaId, @Param("compId") Long competenciaId);

    // SOMA DE PESSOAL (Apenas RH)
    @Query("""
        SELECT SUM(p.valor * COALESCE(p.quantidade, 1)) 
        FROM Preenchimento p 
        WHERE (:estId IS NULL OR p.estruturaAdm.id = :estId) 
          AND (:compId IS NULL OR p.competencia.id = :compId)
          AND p.item.classificacao.nomeClassificacao = 'Recursos Humanos'
    """)
    BigDecimal somarPessoal(@Param("estId") Long estruturaId, @Param("compId") Long competenciaId);
}