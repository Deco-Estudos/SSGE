package com.example.SEED.Rh;

import java.math.BigDecimal;

public record RhPreenchimentoLoteDTO(
        Long comboDestinoId,
        Integer quantidade,
        BigDecimal valor
) {}