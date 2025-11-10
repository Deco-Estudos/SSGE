package com.example.SEED.Preenchimento;

import java.math.BigDecimal;

public record PreenchimentoResponseDTO(
        Long itemId,
        BigDecimal valor,
        String observacao
) {}
