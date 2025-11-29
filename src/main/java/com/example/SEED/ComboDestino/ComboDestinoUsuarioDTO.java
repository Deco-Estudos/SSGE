package com.example.SEED.ComboDestino;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ComboDestinoUsuarioDTO(
        Long id,
        Long comboId,
        String nomeCombo,
        Long setorId,
        String nomeSetor,
        LocalDateTime dataEnvio,
        LocalDate dataFim // renomeou
) {}

