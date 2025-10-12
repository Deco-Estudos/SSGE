package com.example.SEED.Municipio;

import com.example.SEED.Uf.Uf;

public record MunicipioDTO(
        Long id,
        String nome,
        Uf uf,
        String codeIbge
) {}
