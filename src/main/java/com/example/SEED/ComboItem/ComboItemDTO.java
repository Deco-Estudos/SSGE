package com.example.SEED.ComboItem;

import com.example.SEED.Combo.ComboDTO;
import com.example.SEED.Item.ItemDTO;
import com.example.SEED.model.CompetenciaStatus;

import java.util.Date;

public record ComboItemDTO(
        Long id,
        ComboDTO comboDTO,
        ItemDTO itemDTO,
        String ordem,
        Boolean obrigatorio
) {
    public static record CompetenciaDTO(
            Long id,
            int ano,
            String mes,
            Date dataInicio,
            Date dataFim,
            CompetenciaStatus competenciaStatus

    ) {
    }
}
