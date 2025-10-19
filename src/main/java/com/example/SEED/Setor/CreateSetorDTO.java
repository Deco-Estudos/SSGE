package com.example.SEED.Setor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSetorDTO(
        @NotBlank String nome,
        String descricao,
        @NotNull Long estruturaId
) {}