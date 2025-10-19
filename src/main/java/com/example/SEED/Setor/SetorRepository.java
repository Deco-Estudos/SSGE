package com.example.SEED.Setor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SetorRepository extends JpaRepository<Setor,Long> {
    List<Setor> findByEstruturaAdmId(Long estruturaId);
}
