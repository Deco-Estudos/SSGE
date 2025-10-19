package com.example.SEED.ComboDestino;


import com.example.SEED.Combo.Combo;

import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import com.example.SEED.Setor.SetorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboDestinoService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private EstruturaAdmRepository estruturaRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private ComboDestinoRepository destinoRepository;

    @Autowired
    private SetorService setorService;

    public void enviarCombo(Long comboId, Long setorId, Long estruturaId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado: " + comboId));

        EstruturaAdm estrutura = null;
        if (estruturaId != null) {
            estrutura = estruturaRepository.findById(estruturaId)
                    .orElseThrow(() -> new EntityNotFoundException("Estrutura não encontrada: " + estruturaId));
        }

        Setor setor = setorId != null
                ? setorRepository.findById(setorId).orElse(null)
                : null;

        ComboDestino destino = ComboDestino.builder()
                .combo(combo)
                .estruturaAdm(estrutura)
                .setor(setor)
                .ativo(true)
                .build();

        destinoRepository.save(destino);
    }

}
