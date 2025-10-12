package com.example.SEED.dto;

import com.example.SEED.Combo.ComboDTO;
import com.example.SEED.Item.ItemDTO;
import com.example.SEED.model.SolicitacaoStatus;
import  com.example.SEED.Usuario.UsuarioDTO;

import java.util.Date;

public record SolicitaItemComboDTO(
        Long id,
        ComboDTO comboDTO,
        ItemDTO itemDTO,
        UsuarioDTO usuarioDTO,
        String justificativa,
        Date dataSolicitacao,
        SolicitacaoStatus status
) {
}
