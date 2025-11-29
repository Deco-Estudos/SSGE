package com.example.SEED.UsuarioApi;

import com.example.SEED.Setor.SetorDTO;
import com.example.SEED.Setor.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
@CrossOrigin(origins = "http://localhost:5173") // Permite acesso do front-end
public class SetorUsuarioController {

    @Autowired
    private SetorService setorService;

    @GetMapping("/estrutura/{estruturaId}")
    public ResponseEntity<List<SetorDTO>> listByEstrutura(@PathVariable Long estruturaId) {
        // Reutiliza o metodo que já existia no SetorService
        return ResponseEntity.ok(setorService.findByEstrutura(estruturaId));
    }
}