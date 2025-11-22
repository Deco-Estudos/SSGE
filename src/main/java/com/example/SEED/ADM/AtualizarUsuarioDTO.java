package com.example.SEED.ADM;

import java.util.List;

public record AtualizarUsuarioDTO(
        Long perfilId,       // ID do Cargo (ex: DIRETOR ou RESPONSAVEL)
        List<Long> setorIds, // Para Responsáveis de Setor
        List<Long> estruturaIds //  Para Diretores (vínculo direto com a escola)
) {}