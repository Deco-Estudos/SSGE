package com.example.SEED.dto;

import com.example.SEED.model.CompetenciaStatus;

import java.util.Date;

public record CompetenciaDTO(
        Long id,
        int ano,
        String mes,
        Date dataInicio,
        Date dataFim,
        CompetenciaStatus competenciaStatus

) {
}
