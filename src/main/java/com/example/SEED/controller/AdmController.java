package com.example.SEED.controller;

import com.example.SEED.Usuario.UsuarioDTO;
import com.example.SEED.service.AdmService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adm")
public class AdmController {
    @Autowired
    AdmService admService;

    @GetMapping("/usuarios-pendentes")
    public ResponseEntity<List<UsuarioDTO>> listarPendentes(){
        List<UsuarioDTO> dtos = admService.listarUsers();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/aprovar/{id}")
    public ResponseEntity<String> aprovarUsuario(@PathVariable Long id) {
        String mensagem = admService.aprovarUser(id);
        return ResponseEntity.ok(mensagem);
    }

    @DeleteMapping("/reprovar/{id}")
    public ResponseEntity<String> reprovarUsuario(@PathVariable Long id) {
        String mensagem = admService.reprovarUser(id);
        return ResponseEntity.ok(mensagem);
    }
}
