package com.example.SEED.dto;

import java.util.Date;

public record UsuarioPerfilEstruturaDTO(
        Long id,
        UsuarioDTO usuario,
        PerfilDTO perfil,
        EstruturaAdmDTO estruturaAdm,
        Date dataAssociacao,
        boolean ativo
) {}
