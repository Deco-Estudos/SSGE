package com.example.SEED.Censo;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CensoRepository extends JpaRepository<Censo, Long> {

    Optional<Censo> findByEstruturaAdmAndCompetencia(EstruturaAdm estruturaAdm, Competencia competencia);


    @Query("SELECT SUM(c.quantidadeAlunos) FROM Censo c WHERE (:estId IS NULL OR c.estruturaAdm.id = :estId) AND (:compId IS NULL OR c.competencia.id = :compId)")
    Long somarAlunos(@Param("estId") Long estruturaId, @Param("compId") Long competenciaId);
}