package com.example.SEED.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String perfil
) {}