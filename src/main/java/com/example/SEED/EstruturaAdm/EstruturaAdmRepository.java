package com.example.SEED.EstruturaAdm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstruturaAdmRepository extends JpaRepository<EstruturaAdm, Long> {
    List<EstruturaAdm> findByTipo(TipoEstrutura tipo);
    List<EstruturaAdm> findByAtivoTrue();
}
