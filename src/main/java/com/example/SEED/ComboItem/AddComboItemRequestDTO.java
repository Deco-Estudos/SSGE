package com.example.SEED.ComboItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddComboItemRequestDTO(
        @NotNull(message = "O ID do item é obrigatório.")
        Long itemId,

        @NotBlank(message = "A ordem é obrigatória.")
        String ordem,

        @NotNull(message = "O campo 'obrigatório' é obrigatório.")
        Boolean obrigatorio,


        @Min(value = 0, message = "O valor não pode ser negativo.")
        Integer valor
) {}