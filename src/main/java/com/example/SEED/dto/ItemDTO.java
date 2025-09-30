package com.example.SEED.dto;

public record ItemDTO(
        Long id,
        String nomeItem,
        String descricao,
        ClassificacaoDTO classificacaoDTO
) {


}
