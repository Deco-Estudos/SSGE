// src/main/java/com/example/SEED/Classificacao/ClassificacaoService.java

package com.example.SEED.Classificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;


    private ClassificacaoDTO toDTO(Classificacao classificacao) {
        return new ClassificacaoDTO(
                classificacao.getId(),
                classificacao.getNomeClassificacao(),
                classificacao.getDescricao()
        );
    }

    @Transactional(readOnly = true)
    public List<ClassificacaoDTO> findAll() {
        return classificacaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}