package com.example.SEED.Combo;

import java.util.Date;

public record ComboDTO(
        Long id,
        String nomeCombo,
        String descricao,
        boolean ativo,
        Date dataCriacao

) {
}
