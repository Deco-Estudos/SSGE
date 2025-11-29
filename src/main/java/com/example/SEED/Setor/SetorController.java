package com.example.SEED.Setor;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/adm/setores")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @PostMapping
    public ResponseEntity<SetorDTO> create(@Valid @RequestBody CreateSetorDTO dto) {
        SetorDTO newSetor = setorService.create(dto);
        return new ResponseEntity<>(newSetor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SetorDTO>> listAll() {
        return ResponseEntity.ok(setorService.findAll());
    }

    @GetMapping("/estrutura/{estruturaId}")
    public ResponseEntity<List<SetorDTO>> listByEstrutura(@PathVariable Long estruturaId) {
        return ResponseEntity.ok(setorService.findByEstrutura(estruturaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateSetorDTO dto) {
        return ResponseEntity.ok(setorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        setorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
