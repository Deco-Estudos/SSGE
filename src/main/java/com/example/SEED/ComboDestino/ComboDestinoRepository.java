package com.example.SEED.ComboDestino;

import com.example.SEED.Combo.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboDestinoRepository extends JpaRepository<ComboDestino, Long> {
    // Retorna todos os destinos de um combo
    List<ComboDestino> findByCombo(Combo combo);
}
