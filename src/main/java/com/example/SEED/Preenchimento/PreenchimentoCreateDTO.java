package com.example.SEED.Preenchimento;

import java.math.BigDecimal;

public record PreenchimentoCreateDTO(
        Long itemId,
        BigDecimal valor,
        String observacao
) {}