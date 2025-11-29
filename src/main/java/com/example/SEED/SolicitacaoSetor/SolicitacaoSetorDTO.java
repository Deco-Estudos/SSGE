package com.example.SEED.SolicitacaoSetor;

import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Usuario.Usuario;

import java.time.LocalDateTime;

// DTO de resposta para o ADM
public record SolicitacaoSetorDTO(
        Long id,
        String nomeUsuario,
        String emailUsuario,
        String nomeSetor,
        String nomeEstrutura,
        String justificativa,
        SolicitacaoSetorStatus status,
        LocalDateTime dataSolicitacao
) {

    public SolicitacaoSetorDTO(SolicitacaoSetor solicitacao) {
        this(
                solicitacao.getId(),
                solicitacao.getUsuario().getNome(),
                solicitacao.getUsuario().getEmail(),
                solicitacao.getSetor().getNome(),
                solicitacao.getSetor().getEstruturaAdm().getName(), // Pega o nome da Estrutura
                solicitacao.getJustificativa(),
                solicitacao.getStatus(),
                solicitacao.getDataSolicitacao()
        );
    }
}