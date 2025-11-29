
package com.example.SEED.Classificacao;

import jakarta.persistence.EntityNotFoundException; // Importar
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


    @Transactional(readOnly = true)
    public ClassificacaoDTO findById(Long id) {
        return classificacaoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Classificação não encontrada com o ID: " + id));
    }


    @Transactional
    public ClassificacaoDTO create(ClassificacaoDTO dto) {
        Classificacao classificacao = new Classificacao();
        classificacao.setNomeClassificacao(dto.nomeClassificacao());
        classificacao.setDescricao(dto.descricao());
        Classificacao saved = classificacaoRepository.save(classificacao);
        return toDTO(saved);
    }


    @Transactional
    public ClassificacaoDTO update(Long id, ClassificacaoDTO dto) {
        Classificacao classificacao = classificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classificação não encontrada com o ID: " + id));

        classificacao.setNomeClassificacao(dto.nomeClassificacao());
        classificacao.setDescricao(dto.descricao());
        Classificacao updated = classificacaoRepository.save(classificacao);
        return toDTO(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (!classificacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Classificação não encontrada com o ID: " + id);
        }
        classificacaoRepository.deleteById(id);
    }
}