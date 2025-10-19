package com.example.SEED.ComboDestino;

public record ComboDestinoDTO(
        Long id,
        Long comboId,
        Long estruturaId,
        Long setorId,
        Boolean enviado
) {}

