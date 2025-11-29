package com.example.SEED.CompetenciaReabertura;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competencia/reabertura")
public class CompetenciaReaberturaController {

    private final CompetenciaReaberturaService service;

    public CompetenciaReaberturaController(CompetenciaReaberturaService service) {
        this.service = service;
    }

    // RESPONSÁVEL SOLICITA
    @PostMapping("/solicitar")
    public void solicitar(@RequestBody CompetenciaReaberturaCreateDTO dto) {
        service.solicitarReabertura(dto);
    }

    // ADM APROVA
    @PostMapping("/aprovar")
    public void aprovar(@RequestBody CompetenciaReaberturaAprovacaoDTO dto) {
        service.aprovar(dto);
    }

    // ADM NEGA
    @PostMapping("/negar/{id}")
    public void negar(@PathVariable Long id) {
        service.negar(id);
    }
}
