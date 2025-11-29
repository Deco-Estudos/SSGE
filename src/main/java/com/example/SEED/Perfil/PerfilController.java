package com.example.SEED.Perfil;

import com.example.SEED.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adm/perfis")
@CrossOrigin(origins = "http://localhost:5173")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping
    public ResponseEntity<List<Perfil>> listarPerfis() {

        // Em vez de buscar todos, buscamos todos EXCETO o ADM.
        List<Perfil> perfisFiltrados = perfilRepository.findByNomePerfilNot(NomePerfil.ADM);
        return ResponseEntity.ok(perfisFiltrados);
    }
}