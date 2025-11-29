package com.example.SEED.Setor;

import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private EstruturaAdmRepository estruturaRepository;

    @Transactional
    public SetorDTO create(CreateSetorDTO dto) {
        EstruturaAdm estrutura = estruturaRepository.findById(dto.estruturaId())
                .orElseThrow(() -> new IllegalArgumentException("Estrutura não encontrada"));

        Setor setor = Setor.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .estruturaAdm(estrutura)
                .build();

        setorRepository.save(setor);
        return new SetorDTO(setor.getId(), setor.getNome(), setor.getDescricao(), estrutura.getId());
    }

    public List<SetorDTO> findAll() {
        return setorRepository.findAll()
                .stream()
                .map(s -> new SetorDTO(s.getId(), s.getNome(), s.getDescricao(), s.getEstruturaAdm().getId()))
                .toList();
    }

    public List<SetorDTO> findByEstrutura(Long estruturaId) {
        return setorRepository.findByEstruturaAdmId(estruturaId)
                .stream()
                .map(s -> new SetorDTO(s.getId(), s.getNome(), s.getDescricao(), s.getEstruturaAdm().getId()))
                .toList();
    }

    @Transactional
    public SetorDTO update(Long id, UpdateSetorDTO dto) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));

        if (dto.nome() != null) setor.setNome(dto.nome());
        if (dto.descricao() != null) setor.setDescricao(dto.descricao());

        setorRepository.save(setor);

        return new SetorDTO(setor.getId(), setor.getNome(), setor.getDescricao(), setor.getEstruturaAdm().getId());
    }

    @Transactional
    public void delete(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));
        setorRepository.delete(setor);
    }
}
