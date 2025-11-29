package com.example.SEED.Perfil;

public record PerfilDTO(
        Long id,
        String nomePerfil,
        String descricao,
        int nivelAcesso
        ) {
}
