package com.example.SEED.SolicitacaoSetor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSolicitacaoSetorDTO(
        @NotNull(message = "O ID do Setor é obrigatório.")
        Long setorId,

        @NotBlank(message = "A justificativa é obrigatória.")
        String justificativa
) {}