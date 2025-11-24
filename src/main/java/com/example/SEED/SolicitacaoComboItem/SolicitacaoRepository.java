package com.example.SEED.SolicitacaoComboItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    List<Solicitacao> findBySolicitanteId(Long userId);
}
