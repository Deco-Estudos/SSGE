package com.example.SEED.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PreenchimentoDTO(
        Long id,
        ItemDTO itemDTO,
        UsuarioDTO usuarioDTO,
        EstruturaAdmDTO estruturaAdmDTO,
        CompetenciaDTO competenciaDTO,
        BigDecimal valor,
        Date dataPreenchimento,
        String observacao
) {}
