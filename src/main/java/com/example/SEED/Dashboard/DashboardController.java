package com.example.SEED.Dashboard;

import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private EstruturaAdmService estruturaAdmService;

    @GetMapping
    public ResponseEntity<?> getResumo(
            @RequestParam(required = false) Long estruturaId,
            @RequestParam(required = false) Long competenciaId
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();


        if (role.equals("ADM") || role.equals("ROLE_ADM") || role.equals("RH") || role.equals("ROLE_RH")) {
            return ResponseEntity.ok(dashboardService.calcularResumo(estruturaId, competenciaId));
        }


        List<EstruturaAdmDTO> minhasEstruturas = estruturaAdmService.listarEstruturasDoUsuario(email);

        if (minhasEstruturas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Usuário não vinculado a nenhuma escola. Solicite acesso a um setor.");
        }


        Long minhaEstruturaId = minhasEstruturas.get(0).id();


        if (estruturaId != null && !estruturaId.equals(minhaEstruturaId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Você não tem permissão para ver dados desta escola.");
        }

        // Retorna os dados forçando o ID da escola dele
        return ResponseEntity.ok(dashboardService.calcularResumo(minhaEstruturaId, competenciaId));
    }
}