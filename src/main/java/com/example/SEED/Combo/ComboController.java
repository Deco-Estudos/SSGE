package com.example.SEED.Combo;

import com.example.SEED.ComboDestino.ComboDestinoDTO;
import com.example.SEED.ComboDestino.ComboDestinoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SEED.ComboItem.ComboItemService;
import java.util.List;
import com.example.SEED.ComboItem.AddComboItemRequestDTO;
import com.example.SEED.ComboItem.ComboItemResponseDTO;
import com.example.SEED.ComboItem.ComboItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/adm/combos") // Endpoint base para todas as operações de combo
public class ComboController {

    @Autowired
    private ComboService comboService;

    // --- INJEÇÃO DO NOVO SERVICE ---
    @Autowired
    private ComboItemService comboItemService;

    @Autowired
    private ComboDestinoService comboDestinoService;

    @PostMapping
    public ResponseEntity<ComboDTO> createCombo(@Valid @RequestBody ComboDTO comboDTO) {
        ComboDTO newCombo = comboService.create(comboDTO);
        return new ResponseEntity<>(newCombo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComboDTO>> getAllCombos() {
        List<ComboDTO> combos = comboService.findAll();
        return ResponseEntity.ok(combos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboDTO> getComboById(@PathVariable Long id) {
        ComboDTO combo = comboService.findById(id);
        return ResponseEntity.ok(combo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComboDTO> updateCombo(@PathVariable Long id, @Valid @RequestBody ComboDTO comboDTO) {
        ComboDTO updatedCombo = comboService.update(id, comboDTO);
        return ResponseEntity.ok(updatedCombo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        comboService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adiciona um item a um combo específico.
     */
    @PostMapping("/{comboId}/itens")
    public ResponseEntity<ComboItemResponseDTO> addItemToCombo(
            @PathVariable Long comboId,
            @Valid @RequestBody AddComboItemRequestDTO requestDTO) {

        ComboItemResponseDTO newComboItem = comboItemService.addItemToCombo(comboId, requestDTO);
        return new ResponseEntity<>(newComboItem, HttpStatus.CREATED);
    }

    /**
     * Lista todos os itens de um combo específico, ordenados.
     */
    @GetMapping("/{comboId}/itens")
    public ResponseEntity<List<ComboItemResponseDTO>> getItemsForCombo(@PathVariable Long comboId) {
        List<ComboItemResponseDTO> items = comboItemService.getItemsForCombo(comboId);
        return ResponseEntity.ok(items);
    }

    /**
     * Remove um item de um combo.
     * Note que o ID é o da associação (combo_item), não do item.
     */
    @DeleteMapping("/itens/{comboItemId}")
    public ResponseEntity<Void> removeItemFromCombo(@PathVariable Long comboItemId) {
        comboItemService.removeItemFromCombo(comboItemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{comboId}/setor/{setorId}")
    public ResponseEntity<ComboDestinoDTO> vincularComboAoSetor(
            @PathVariable Long comboId,
            @PathVariable Long setorId,
            @RequestParam(required = false) Long estruturaId) {

        comboDestinoService.enviarCombo(comboId, setorId, estruturaId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
