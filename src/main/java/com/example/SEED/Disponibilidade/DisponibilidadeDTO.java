package com.example.SEED.Disponibilidade;

import com.example.SEED.Combo.ComboDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;

import java.util.Date;

public record DisponibilidadeDTO(
        Long id,
        ComboDTO comboDTO,
        EstruturaAdmDTO estruturaAdmDTO,
        Date dataInicio,
        Date dataFim,
        DisponibilidadeStatus disponibilidadeStatus

) {
}
