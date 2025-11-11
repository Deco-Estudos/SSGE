package com.example.SEED.ComboItem;

import com.example.SEED.Item.ItemDTO;

public record ComboItemResponseDTO(
        Long id, // Este é o ID da *associação* (da tabela combo_item)
        String ordem,
        Boolean obrigatorio,
        Integer valor,
        ItemDTO item // Aqui incluímos os detalhes completos do item
) {
}