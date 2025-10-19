package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.MunicipioDTO;

public record EstruturaAdmDTO(
        Long id,
        String name,
        TipoEstrutura tipo,
        MunicipioDTO municipio,
        boolean ativo,
        String cep
) {}
