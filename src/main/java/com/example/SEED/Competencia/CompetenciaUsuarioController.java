package com.example.SEED.UsuarioApi;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/competencias")
@CrossOrigin(origins = "http://localhost:5173")
public class CompetenciaUsuarioController {

    @Autowired
    private CompetenciaRepository repository;

    @GetMapping
    public ResponseEntity<List<Competencia>> listarAtivas() {

        return ResponseEntity.ok(repository.findAll());
    }
}