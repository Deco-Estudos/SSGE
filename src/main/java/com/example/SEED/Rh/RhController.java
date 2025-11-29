package com.example.SEED.Rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rh")
@CrossOrigin(origins = "http://localhost:5173")
public class RhController {

    @Autowired
    private RhService rhService;

    // 1. Listar Itens (Cargos) disponíveis para preenchimento
    @GetMapping("/itens-disponiveis")
    public ResponseEntity<List<RhItemDTO>> listarItensDisponiveis() {
        return ResponseEntity.ok(rhService.listarItensRhDisponiveis());
    }

    // 2. Listar Escolas para um Item específico
    @GetMapping("/itens/{itemId}/escolas")
    public ResponseEntity<List<RhEscolaEntryDTO>> listarEscolasPorItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(rhService.listarEscolasPorItem(itemId));
    }

    // 3. Salvar em Lote (Várias escolas de uma vez)
    @PostMapping("/itens/{itemId}/salvar-lote")
    public ResponseEntity<Void> salvarLote(
            @PathVariable Long itemId,
            @RequestBody List<RhPreenchimentoLoteDTO> dados) {
        rhService.salvarLote(itemId, dados);
        return ResponseEntity.ok().build();
    }
}