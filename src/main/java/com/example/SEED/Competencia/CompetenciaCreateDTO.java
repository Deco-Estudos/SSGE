package com.example.SEED.Competencia;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompetenciaCreateDTO {
    private int ano;
    private String mes;
    private String nome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}