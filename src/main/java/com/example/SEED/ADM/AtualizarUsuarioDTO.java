package com.example.SEED.ADM;

import java.util.List;

public record AtualizarUsuarioDTO(
        Long perfilId,
        List<Long> setorIds
) {}