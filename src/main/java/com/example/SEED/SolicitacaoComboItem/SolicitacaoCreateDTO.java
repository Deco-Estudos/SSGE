package com.example.SEED.SolicitacaoComboItem;

public record SolicitacaoCreateDTO(
        TipoSolicitacao tipo,
        String nome,
        String descricao,
        Long solicitanteId, // aqui você só manda o ID
        String setor,
        String estrutura
) {}
