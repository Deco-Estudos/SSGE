package com.example.SEED.dto;

public record ComboItemDTO(
        Long id,
        ComboDTO comboDTO,
        ItemDTO itemDTO,
        String ordem,
        Boolean obrigatorio
) {
}
