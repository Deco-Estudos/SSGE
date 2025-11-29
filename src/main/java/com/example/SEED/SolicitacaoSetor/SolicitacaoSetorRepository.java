package com.example.SEED.SolicitacaoSetor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoSetorRepository extends JpaRepository<SolicitacaoSetor, Long> {

    List<SolicitacaoSetor> findByStatus(SolicitacaoSetorStatus status);
}