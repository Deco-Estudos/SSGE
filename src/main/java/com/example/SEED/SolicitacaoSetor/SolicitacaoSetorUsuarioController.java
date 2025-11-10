package com.example.SEED.SolicitacaoSetor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsavel-setor/solicitacao-setor")
@CrossOrigin(origins = "http://localhost:5173")
public class SolicitacaoSetorUsuarioController {

    @Autowired
    private SolicitacaoSetorService solicitacaoSetorService;

    @PostMapping
    public ResponseEntity<Void> criarSolicitacao(@RequestBody CreateSolicitacaoSetorDTO dto) {
        solicitacaoSetorService.createSolicitacao(dto);
        return ResponseEntity.ok().build();
    }
}
