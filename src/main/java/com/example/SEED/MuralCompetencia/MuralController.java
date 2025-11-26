package com.example.SEED.MuralCompetencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm/mural")
public class MuralController {
    @Autowired
    private MuralCompetenciaService service;

    @GetMapping("/{competenciaId}")
    public List<Object> getMural(
            @PathVariable Long competenciaId,
            @RequestParam(required = false) String perfil
    ) {
        return service.getMuralPorCompetencia(competenciaId, perfil);
    }
}
