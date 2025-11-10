package com.example.SEED.Preenchimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsavel-setor/preenchimentos")
@CrossOrigin(origins = "http://localhost:5173")
public class PreenchimentoController {

    @Autowired
    private PreenchimentoService preenchimentoService;

    @PostMapping("/{comboDestinoId}")
    public ResponseEntity<Void> salvarPreenchimentos(
            @PathVariable Long comboDestinoId,
            @RequestBody List<PreenchimentoCreateDTO> preenchimentos
    ) {
        preenchimentoService.salvarPreenchimentos(comboDestinoId, preenchimentos);
        return ResponseEntity.ok().build();
    }
}