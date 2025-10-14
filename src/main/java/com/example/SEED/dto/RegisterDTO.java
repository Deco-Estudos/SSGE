package com.example.SEED.dto;

import com.example.SEED.model.NomePerfil;

public record RegisterDTO(
        String email,
        String senha,
        String nome,
        String cpf,
        String telefone,
        NomePerfil nomePerfil
) {
}

