package com.example.SEED.Setor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor,Long> {
    List<Setor> findByEstruturaAdmId(Long estruturaId);
    Optional<Setor> findById(Long id);
    List<Setor> findByResponsaveisId(Long usuarioId);
    List<Setor> findByResponsaveisIdAndEstruturaAdmId(Long usuarioId, Long estruturaId);
}
