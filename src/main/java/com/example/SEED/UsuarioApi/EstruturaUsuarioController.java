package com.example.SEED.UsuarioApi;

import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estruturas")
@CrossOrigin(origins = "http://localhost:5173")
public class EstruturaUsuarioController {

    @Autowired
    private EstruturaAdmService estruturaAdmService;

    @GetMapping
    public ResponseEntity<List<EstruturaAdmDTO>> listarEstruturas(
            @RequestParam(required = false, defaultValue = "false") boolean todas
    ) {
        // Se o front-end pedir ?todas=true, retorna a lista geral de ativas (Para Solicitação)
        if (todas) {
            return ResponseEntity.ok(estruturaAdmService.listarEstruturasAtivas());
        }

        // Lógica Padrão (Para Dashboard e Preenchimento)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();

        // ADM/RH sempre veem tudo
        if (role.equals("ADM") || role.equals("ROLE_ADM") || role.equals("RH") || role.equals("ROLE_RH")) {
            return ResponseEntity.ok(estruturaAdmService.listarEstruturasAtivas());
        }

        // Outros veem apenas as suas, a menos que tenham pedido "todas" (que caiu no if lá de cima)
        return ResponseEntity.ok(estruturaAdmService.listarEstruturasDoUsuario(email));
    }
}