package com.example.SEED.ComboItem;

import com.example.SEED.Item.ItemDTO;

public record ComboItemResponseDTO(
        Long id,
        String ordem,
        Boolean obrigatorio,
        ItemDTO item
) {}