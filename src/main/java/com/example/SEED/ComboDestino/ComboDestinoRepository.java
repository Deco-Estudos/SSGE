package com.example.SEED.ComboDestino;

import com.example.SEED.Combo.Combo;
import com.example.SEED.Setor.Setor; // Importado
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set; // Importado

@Repository
public interface ComboDestinoRepository extends JpaRepository<ComboDestino, Long> {

    List<ComboDestino> findByCombo(Combo combo);
    List<ComboDestino> findBySetorIn(Set<Setor> setores);
    List<ComboDestino> findBySetorIdInAndAtivoTrue(List<Long> setoresId);
    List<ComboDestino> findBySetorIdAndAtivoTrue(Long setorId);

}