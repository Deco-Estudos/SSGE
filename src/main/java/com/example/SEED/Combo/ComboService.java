package com.example.SEED.Combo;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;

    // Metodo para converter Entidade para DTO
    private ComboDTO toDTO(Combo combo) {
        return new ComboDTO(
                combo.getId(),
                combo.getNomeCombo(),
                combo.getDescricao(),
                combo.isAtivo(),
                combo.getDataCriacao()
        );
    }

    @Transactional(readOnly = true)
    public List<ComboDTO> findAll() {
        return comboRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComboDTO findById(Long id) {
        return comboRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado com o ID: " + id));
    }

    @Transactional
    public ComboDTO create(ComboDTO comboDTO) {
        Combo combo = new Combo();
        combo.setNomeCombo(comboDTO.nomeCombo());
        combo.setDescricao(comboDTO.descricao());
        combo.setDataCriacao(new Date()); // Define a data de criação no momento do salvamento
        combo.setAtivo(true); // Por padrão, um novo combo é criado como ativo

        Combo savedCombo = comboRepository.save(combo);
        return toDTO(savedCombo);
    }

    @Transactional
    public ComboDTO update(Long id, ComboDTO comboDTO) {
        Combo combo = comboRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado com o ID: " + id));

        combo.setNomeCombo(comboDTO.nomeCombo());
        combo.setDescricao(comboDTO.descricao());
        combo.setAtivo(comboDTO.ativo());

        Combo updatedCombo = comboRepository.save(combo);
        return toDTO(updatedCombo);
    }

    @Transactional
    public void delete(Long comboId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado"));

        // Deleta todos os destinos associados
        List<ComboDestino> destinos = comboDestinoRepository.findByCombo(combo);
        if (!destinos.isEmpty()) {
            comboDestinoRepository.deleteAll(destinos);
        }

        // Agora deleta o combo
        comboRepository.delete(combo);
    }
}