package com.example.SEED.Rh;

import java.math.BigDecimal;

public record RhEscolaEntryDTO(
        Long comboDestinoId, // O ID do envio para aquela escola
        String nomeEstrutura, // Nome da Escola
        Integer quantidadeAtual, // O que já está salvo no banco
        BigDecimal valorAtual    // O que já está salvo no banco
) {}