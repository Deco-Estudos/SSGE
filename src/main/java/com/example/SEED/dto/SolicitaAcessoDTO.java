package com.example.SEED.dto;

import com.example.SEED.model.SolicitacaoStatus;

import java.util.Date;

public record SolicitaAcessoDTO(
        Long id,
        UsuarioDTO usuario,
        PerfilDTO perfil,
        EstruturaAdmDTO estruturaAdm,
        Date dataSolicitacao,
        SolicitacaoStatus solicitacaoStatus
) {}
