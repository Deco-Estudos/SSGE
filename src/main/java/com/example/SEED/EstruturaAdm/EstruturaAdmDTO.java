package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.MunicipioDTO;
import com.example.SEED.model.TipoEstrutura;

public record EstruturaAdmDTO(
        Long id,
        String name,
        TipoEstrutura tipo,
        MunicipioDTO municipio
) {}
