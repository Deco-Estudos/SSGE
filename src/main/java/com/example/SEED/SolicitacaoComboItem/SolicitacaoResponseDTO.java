package com.example.SEED.SolicitacaoComboItem;

import java.time.LocalDateTime;

public record SolicitacaoResponseDTO(
        Long id,
        TipoSolicitacao tipo,
        String nome,
        String descricao,
        Long solicitanteId,
        String solicitanteNome,
        String setor,
        String setorNome,
        String estrutura,
        String estruturaNome,
        StatusSolicitacao status,
        String feedbackAdm,
        LocalDateTime dataCriacao,
        LocalDateTime dataConclusao
) {}
