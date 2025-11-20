package com.example.SEED.Censo;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CensoRepository extends JpaRepository<Censo, Long> {

    Optional<Censo> findByEstruturaAdmAndCompetencia(EstruturaAdm estruturaAdm, Competencia competencia);
}