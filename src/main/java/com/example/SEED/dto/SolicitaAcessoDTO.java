package com.example.SEED.dto;

import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.Perfil.PerfilDTO;
import com.example.SEED.model.SolicitacaoStatus;
import  com.example.SEED.Usuario.UsuarioDTO;

import java.util.Date;

public record SolicitaAcessoDTO(
        Long id,
        UsuarioDTO usuario,
        PerfilDTO perfil,
        EstruturaAdmDTO estruturaAdm,
        Date dataSolicitacao,
        SolicitacaoStatus solicitacaoStatus
) {}
