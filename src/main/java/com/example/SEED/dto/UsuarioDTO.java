package com.example.SEED.dto;

import com.example.SEED.model.NomePerfil;

import java.util.Date;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        NomePerfil nomePerfil,
        Date dataCadastro,
        boolean ativo
        //Não deve exibir a senha
) {}
