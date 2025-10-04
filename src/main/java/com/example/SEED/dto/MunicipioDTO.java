package com.example.SEED.dto;

import com.example.SEED.model.Uf;

public record MunicipioDTO(
        Long id,
        String nome,
        Uf uf,
        String codeIbge
) {}
