package com.example.SEED.Preenchimento;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record PreenchimentoCreateDTO(
        Long itemId,

        @Min(value = 0, message = "O valor em R$ não pode ser negativo.")
        BigDecimal valor,

        @Min(value = 0, message = "A quantidade não pode ser negativa.")
        Integer quantidade,

        String observacao
) {}