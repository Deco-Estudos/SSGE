package com.example.SEED.Classificacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {
}