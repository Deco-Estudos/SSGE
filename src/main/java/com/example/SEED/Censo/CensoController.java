package com.example.SEED.Censo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/censo")
@CrossOrigin(origins = "http://localhost:5173")
public class CensoController {

    @Autowired
    private CensoService censoService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody CensoDTO dto) {
        censoService.salvarCenso(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{estruturaId}/{competenciaId}")
    public ResponseEntity<Integer> buscar(@PathVariable Long estruturaId, @PathVariable Long competenciaId) {
        Integer qtd = censoService.buscarQuantidadeAlunos(estruturaId, competenciaId);
        return ResponseEntity.ok(qtd); // Pode retornar null, o front deve tratar
    }
}