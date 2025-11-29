package com.example.SEED.ComboDestino;

import com.example.SEED.Item.ItemDTO;
import com.example.SEED.Setor.SetorDTO;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsavel-setor/combos")
@CrossOrigin(origins = "http://localhost:5173")
public class ComboDestinoUsuarioController {

    @Autowired
    private ComboDestinoUsuarioService comboDestinoUsuarioService;

    @Autowired
    UserRepository userRepository;

    // 🔹 Lista todos os combos destinados ao responsável de setor logado
    @GetMapping
    public ResponseEntity<List<ComboDestinoUsuarioDTO>> listarCombosDoUsuario() {
        return ResponseEntity.ok(comboDestinoUsuarioService.listarCombosDoUsuario());
    }

    @GetMapping("/setor/{setorId}")
    public List<ComboDestinoUsuarioDTO> listarPorSetor(@PathVariable Long setorId) {
        return comboDestinoUsuarioService.listarCombosPorSetor(setorId);
    }

    @GetMapping("/setores")
    public List<SetorDTO> listarSetoresDoUsuario() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + email));

        return usuario.getSetores().stream()
                .map(s -> new SetorDTO(
                        s.getId(),
                        s.getNome(),
                        s.getDescricao(),
                        s.getEstruturaAdm() != null ? s.getEstruturaAdm().getId() : null
                ))
                .toList();
    }



    // 🔹 Lista os itens de um combo específico
    @GetMapping("/{comboId}/itens")
    public ResponseEntity<List<ItemDTO>> listarItensDoCombo(@PathVariable Long comboId) {
        List<ItemDTO> itens = comboDestinoUsuarioService.listarItensDoCombo(comboId);
        return ResponseEntity.ok(itens);
    }
}
