package com.example.SEED.ComboDestino;

import java.time.LocalDate;

public record ComboDestinoDTO(
        Long id,
        String nomeCombo,
        String nomeSetor,
        LocalDate dataEnvio,
        LocalDate competenciaDataFim // <-- pega a dataFim da competência
) {}

