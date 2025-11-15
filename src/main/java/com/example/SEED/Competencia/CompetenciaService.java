package com.example.SEED.Competencia;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenciaService {

    private final CompetenciaRepository repository;

    public CompetenciaService(CompetenciaRepository repository) {
        this.repository = repository;
    }

    // Buscar todas competências
    public List<Competencia> listarTodas() {
        return repository.findAll();
    }

    // Buscar por ID
    public Competencia buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competência não encontrada"));
    }

    // Criar nova competência
    public Competencia criar(CompetenciaCreateDTO dto) {
        Competencia c = Competencia.builder()
                .nome(dto.getNome())
                .ano(dto.getAno())
                .mes(dto.getMes())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .competenciaStatus(CompetenciaStatus.ABERTO) // sempre ABERTO ao criar
                .build();
        return repository.save(c);
    }

    // Atualizar competência existente
    public Competencia atualizar(Long id, CompetenciaDTO dto) {
        Competencia c = buscarPorId(id);
        c.setAno(dto.getAno());
        c.setMes(dto.getMes());
        c.setDataInicio(dto.getDataInicio());
        c.setDataFim(dto.getDataFim());
        if (dto.getCompetenciaStatus() != null) {
            c.setCompetenciaStatus(dto.getCompetenciaStatus());
        }
        return repository.save(c);
    }

    // Deletar competência
    public void deletar(Long id) {
        Competencia c = buscarPorId(id);
        repository.delete(c);
    }

    // Atualizar status da competência
    public Competencia atualizarStatus(Long id, CompetenciaStatus status) {
        Competencia c = buscarPorId(id);
        c.setCompetenciaStatus(status);
        return repository.save(c);
    }
}
