package com.example.SEED.MuralCompetencia;

import java.util.Date;

public record MuralCensoDTO(
        Long id,
        Long competenciaId,
        String competenciaNome,
        String estruturaNome,
        String usuarioNome,
        String usuarioPerfil,
        Integer quantidadeAlunos,
        Date dataPreenchimento,
        String tipo
) {}

