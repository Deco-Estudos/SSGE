// src/main/java/com/example/SEED/Classificacao/ClassificacaoController.java

package com.example.SEED.Classificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adm/classificacoes")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @GetMapping
    public ResponseEntity<List<ClassificacaoDTO>> getAllClassificacoes() {
        List<ClassificacaoDTO> classificacoes = classificacaoService.findAll();
        return ResponseEntity.ok(classificacoes);
    }
}