package com.example.SEED.Combo;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaRepository;
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

    @Autowired
    private CompetenciaRepository competenciaRepository;

    // Converter entidade para DTO
    private ComboDTO toDTO(Combo combo) {
        return new ComboDTO(
                combo.getId(),
                combo.getNomeCombo(),
                combo.getDescricao(),
                combo.isAtivo(),
                combo.getDataCriacao(),
                combo.getCompetencia() != null ? combo.getCompetencia().getId() : null
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
        combo.setDataCriacao(new Date());
        combo.setAtivo(true);

        // Buscar e associar a competência
        Competencia competencia = competenciaRepository.findById(comboDTO.competenciaId())
                .orElseThrow(() -> new EntityNotFoundException("Competência não encontrada com o ID: " + comboDTO.competenciaId()));
        combo.setCompetencia(competencia);

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

        // Atualizar a competência, se fornecida
        if (comboDTO.competenciaId() != null) {
            Competencia competencia = competenciaRepository.findById(comboDTO.competenciaId())
                    .orElseThrow(() -> new EntityNotFoundException("Competência não encontrada com o ID: " + comboDTO.competenciaId()));
            combo.setCompetencia(competencia);
        }

        Combo updatedCombo = comboRepository.save(combo);
        return toDTO(updatedCombo);
    }

    @Transactional
    public void delete(Long comboId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado"));

        // Deleta destinos associados
        List<ComboDestino> destinos = comboDestinoRepository.findByCombo(combo);
        if (!destinos.isEmpty()) {
            comboDestinoRepository.deleteAll(destinos);
        }

        // Deleta o combo
        comboRepository.delete(combo);
    }
}
