package com.example.SEED.ComboDestino;

import com.example.SEED.Item.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsavel-setor/combos")
@CrossOrigin(origins = "http://localhost:5173")
public class ComboDestinoUsuarioController {

    @Autowired
    private ComboDestinoUsuarioService comboDestinoUsuarioService;

    // 🔹 Lista todos os combos destinados ao responsável de setor logado
    @GetMapping
    public ResponseEntity<List<ComboDestino>> listarCombosDoUsuario() {
        return ResponseEntity.ok(comboDestinoUsuarioService.listarCombosDoUsuario());
    }

    // 🔹 Lista os itens de um combo específico
    @GetMapping("/{comboId}/itens")
    public ResponseEntity<List<ItemDTO>> listarItensDoCombo(@PathVariable Long comboId) {
        List<ItemDTO> itens = comboDestinoUsuarioService.listarItensDoCombo(comboId);
        return ResponseEntity.ok(itens);
    }
}
