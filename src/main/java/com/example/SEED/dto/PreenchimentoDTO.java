package com.example.SEED.dto;

import com.example.SEED.ComboItem.ComboItemDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.Item.ItemDTO;
import  com.example.SEED.Usuario.UsuarioDTO;

import java.math.BigDecimal;
import java.util.Date;

public record PreenchimentoDTO(
        Long id,
        ItemDTO itemDTO,
        UsuarioDTO usuarioDTO,
        EstruturaAdmDTO estruturaAdmDTO,
        ComboItemDTO.CompetenciaDTO competenciaDTO,
        BigDecimal valor,
        Date dataPreenchimento,
        String observacao
) {}
