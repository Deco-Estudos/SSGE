package com.example.SEED.Item;

import com.example.SEED.Classificacao.ClassificacaoDTO;

public record ItemDTO(
        Long id,
        String nomeItem,
        String descricao,
        ClassificacaoDTO classificacaoDTO,
        String tipo_dado,
        Boolean obrigatorio,
        Boolean ativo
) {


}
