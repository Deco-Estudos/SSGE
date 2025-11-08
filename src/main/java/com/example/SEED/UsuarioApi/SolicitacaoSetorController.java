package com.example.SEED.UsuarioApi;

import com.example.SEED.SolicitacaoSetor.CreateSolicitacaoSetorDTO;
import com.example.SEED.SolicitacaoSetor.SolicitacaoSetorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitacoes-setor")
@CrossOrigin(origins = "http://localhost:5173")
public class SolicitacaoSetorController {

    @Autowired
    private SolicitacaoSetorService solicitacaoSetorService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateSolicitacaoSetorDTO dto) {
        solicitacaoSetorService.createSolicitacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}