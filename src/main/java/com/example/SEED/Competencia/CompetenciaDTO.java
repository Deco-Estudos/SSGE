package com.example.SEED.Competencia;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompetenciaDTO {
    private Long id;
    private int ano;
    private String mes;
    private String nome; // <- novo campo
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private CompetenciaStatus competenciaStatus;
}
