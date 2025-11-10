package com.example.SEED.Preenchimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsavel-setor/preenchimentos")
public class PreenchimentoController {

    @Autowired
    private PreenchimentoService preenchimentoService;

    // 🔹 Buscar preenchimentos do usuário logado para um comboDestino específico
    @GetMapping("/{comboDestinoId}")
    public ResponseEntity<List<PreenchimentoResponseDTO>> buscarPreenchimentos(
            @PathVariable Long comboDestinoId
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<PreenchimentoResponseDTO> preenchimentos =
                preenchimentoService.buscarPorComboDestino(comboDestinoId, email);

        return ResponseEntity.ok(preenchimentos);
    }

    // 🔹 Salvar novos preenchimentos
    @PostMapping("/{comboDestinoId}")
    public ResponseEntity<Void> salvarPreenchimentos(
            @PathVariable Long comboDestinoId,
            @RequestBody List<PreenchimentoCreateDTO> preenchimentos
    ) {
        preenchimentoService.salvarPreenchimentos(comboDestinoId, preenchimentos);
        return ResponseEntity.ok().build();
    }

    // 🔹 Atualizar preenchimentos existentes
    @PutMapping("/{comboDestinoId}")
    public ResponseEntity<Void> atualizarPreenchimentos(
            @PathVariable Long comboDestinoId,
            @RequestBody List<PreenchimentoCreateDTO> preenchimentos
    ) {
        preenchimentoService.atualizarPreenchimentos(comboDestinoId, preenchimentos);
        return ResponseEntity.ok().build();
    }
}
