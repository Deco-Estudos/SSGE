package com.example.SEED.Competencia;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm/competencias")
public class CompetenciaController {

    private final CompetenciaService service;

    public CompetenciaController(CompetenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Competencia> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Competencia buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Competencia criar(@RequestBody CompetenciaCreateDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public Competencia atualizar(@PathVariable Long id, @RequestBody CompetenciaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    @PatchMapping("/{id}/status")
    public Competencia atualizarStatus(@PathVariable Long id, @RequestParam CompetenciaStatus status) {
        return service.atualizarStatus(id, status);
    }
}
