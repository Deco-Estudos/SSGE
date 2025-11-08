package com.example.SEED.ADM;

import com.example.SEED.SolicitacaoSetor.SolicitacaoSetorDTO;
import com.example.SEED.SolicitacaoSetor.SolicitacaoSetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm/solicitacoes-setor")
@CrossOrigin(origins = "http://localhost:5173") // Permite acesso do front-end
public class SolicitacaoSetorAdmController {

    @Autowired
    private SolicitacaoSetorService solicitacaoSetorService;


    @GetMapping("/pendentes")
    public ResponseEntity<List<SolicitacaoSetorDTO>> getSolicitacoesPendentes() {
        return ResponseEntity.ok(solicitacaoSetorService.listarSolicitacoesPendentes());
    }


    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Void> aprovarSolicitacao(@PathVariable Long id) {
        solicitacaoSetorService.aprovarSolicitacao(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/reprovar")
    public ResponseEntity<Void> reprovarSolicitacao(@PathVariable Long id) {
        solicitacaoSetorService.reprovarSolicitacao(id);
        return ResponseEntity.ok().build();
    }
}