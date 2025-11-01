package com.example.SEED.responsavel_setor;

import com.example.SEED.Combo.Combo;
import com.example.SEED.Combo.ComboDTO;
import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponsavelSetorService {

    @Autowired
    ComboRepository comboRepository;

    public List<ComboDTO> listarCombos() {
        return comboRepository.findAll().stream()
                .map(combo -> new ComboDTO(
                        combo.getId(),
                        combo.getNomeCombo(),
                        combo.getDescricao(),
                        combo.isAtivo(),
                        combo.getDataCriacao()
                )).toList();
    }


}
