package com.example.SEED.dto;

import java.util.Date;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Date dataCadastro,
        boolean ativo
) {}
