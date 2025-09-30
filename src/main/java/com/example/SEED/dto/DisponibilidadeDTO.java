package com.example.SEED.dto;

import com.example.SEED.model.DisponibilidadeStatus;

import java.util.Date;

public record DisponibilidadeDTO(
        Long id,
        ComboDTO comboDTO,
        EstruturaAdmDTO estruturaAdmDTO,
        Date dataInicio,
        Date dataFim,
        DisponibilidadeStatus disponibilidadeStatus

) {
}
