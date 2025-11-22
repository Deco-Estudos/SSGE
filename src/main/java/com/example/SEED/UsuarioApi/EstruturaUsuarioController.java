package com.example.SEED.UsuarioApi;

import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estruturas")
@CrossOrigin(origins = "http://localhost:5173")
public class EstruturaUsuarioController {

    @Autowired
    private EstruturaAdmService estruturaAdmService;

    @GetMapping
    public ResponseEntity<List<EstruturaAdmDTO>> listarEstruturas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Obtém a role do usuário logado
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();

        // 1. Se for ADM ou RH, vê TODAS as ativas
        if (role.equals("ADM") || role.equals("ROLE_ADM") ||
                role.equals("RH") || role.equals("ROLE_RH")) {
            return ResponseEntity.ok(estruturaAdmService.listarEstruturasAtivas());
        }

        // 2. Se for outro perfil (Diretor/Responsável), vê só as SUAS
        return ResponseEntity.ok(estruturaAdmService.listarEstruturasDoUsuario(email));
    }
}