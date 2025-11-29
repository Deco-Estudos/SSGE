package com.example.SEED.ComboDestino;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ComboEnvioDTO {
    private Long estruturaId;
    private List<Long> setoresId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
