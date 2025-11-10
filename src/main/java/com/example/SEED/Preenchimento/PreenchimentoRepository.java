package com.example.SEED.Preenchimento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreenchimentoRepository extends JpaRepository<Preenchimento, Long> {

}
