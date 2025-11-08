package com.example.SEED.UsuarioApi;

import com.example.SEED.EstruturaAdm.EstruturaAdmDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estruturas")
@CrossOrigin(origins = "http://localhost:5173") // Permite acesso do front-end
public class EstruturaUsuarioController {

    @Autowired
    private EstruturaAdmService estruturaAdmService;


    @GetMapping
    public ResponseEntity<List<EstruturaAdmDTO>> getEstruturasAtivas() {
        // Reutiliza o service do ADM, mas chama o novo metodo
        return ResponseEntity.ok(estruturaAdmService.listarEstruturasAtivas());
    }
}