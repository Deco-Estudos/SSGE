package com.example.SEED.dto;

import com.example.SEED.model.TipoEstrutura;

public record EstruturaAdmDTO(
        Long id,
        String name,
        TipoEstrutura tipo,
        MunicipioDTO municipio
) {}
