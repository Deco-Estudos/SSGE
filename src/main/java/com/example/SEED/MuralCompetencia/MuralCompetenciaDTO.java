package com.example.SEED.MuralCompetencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record MuralCompetenciaDTO(
        Long preenchimentoId,
        Long competenciaId,
        String competenciaNome,
        LocalDateTime dataLimite,

        String usuarioNome,
        String usuarioPerfil,

        String estruturaNome,
        String setorNome,

        String comboNome,
        String itemNome,

        Integer quantidade,
        BigDecimal valor,
        Date dataPreenchimento
) {}
