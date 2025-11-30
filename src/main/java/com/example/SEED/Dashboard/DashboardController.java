package com.example.SEED.Dashboard;

import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<?> getDashboardAnual(
            @RequestParam(required = false) Long estruturaId,
            @RequestParam(required = false) Integer ano
    ) {
        // Se não mandar ano, usa o atual
        if (ano == null) ano = LocalDate.now().getYear();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();

        // 1. ADM/RH vê tudo
        if (role.contains("ADM") || role.contains("RH")) {
            return ResponseEntity.ok(dashboardService.gerarDashboardAnual(estruturaId, ano));
        }

        // 2. Diretor/Outros veem só o seu
        List<EstruturaAdmDTO> minhasEstruturas = estruturaAdmService.listarEstruturasDoUsuario(email);
        if (minhasEstruturas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sem vínculo escolar.");
        }

        Long meuId = minhasEstruturas.get(0).id();

        if (estruturaId != null && !estruturaId.equals(meuId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado a esta escola.");
        }

        return ResponseEntity.ok(dashboardService.gerarDashboardAnual(meuId, ano));
    }
}