package com.example.SEED.Dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    @GetMapping
    public ResponseEntity<DashboardResumoDTO> getResumo(
            @RequestParam(required = false) Long estruturaId,
            @RequestParam(required = false) Long competenciaId
    ) {
        return ResponseEntity.ok(dashboardService.calcularResumo(estruturaId, competenciaId));
    }
}