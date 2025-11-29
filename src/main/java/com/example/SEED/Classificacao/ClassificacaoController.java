
package com.example.SEED.Classificacao;

import jakarta.validation.Valid; // Importar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importar
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importar

import java.util.List;

@RestController
@RequestMapping("/adm/classificacoes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @GetMapping
    public ResponseEntity<List<ClassificacaoDTO>> getAllClassificacoes() {
        List<ClassificacaoDTO> classificacoes = classificacaoService.findAll();
        return ResponseEntity.ok(classificacoes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClassificacaoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classificacaoService.findById(id));
    }


    @PostMapping
    public ResponseEntity<ClassificacaoDTO> create(@Valid @RequestBody ClassificacaoDTO dto) {
        ClassificacaoDTO novaClassificacao = classificacaoService.create(dto);
        return new ResponseEntity<>(novaClassificacao, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClassificacaoDTO> update(@PathVariable Long id, @Valid @RequestBody ClassificacaoDTO dto) {
        return ResponseEntity.ok(classificacaoService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classificacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}