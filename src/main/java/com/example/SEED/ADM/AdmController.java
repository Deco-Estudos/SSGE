package com.example.SEED.ADM;

import com.example.SEED.Usuario.UsuarioDTO;
import com.example.SEED.service.AdmService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm")
@CrossOrigin(origins = "http://localhost:5173") // Adicionado CORS
public class AdmController {
    @Autowired
    AdmService admService;

    @GetMapping("/usuarios-pendentes")
    public ResponseEntity<List<UsuarioDTO>> listarPendentes(){
        List<UsuarioDTO> dtos = admService.listarUsers();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> dtos = admService.listarTodosUsuarios();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody AtualizarUsuarioDTO dto) {
        admService.atualizarUsuario(id, dto);
        return ResponseEntity.ok().build();
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