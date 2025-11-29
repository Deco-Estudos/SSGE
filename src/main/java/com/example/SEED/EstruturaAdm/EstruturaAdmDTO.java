package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.MunicipioDTO;

public record EstruturaAdmDTO(
        Long id,
        String name,
        TipoEstrutura tipo,
        MunicipioDTO municipio,
        Boolean ativo,
        String cep,
        Long estruturaPaiId // novo campo opcional
) {}
