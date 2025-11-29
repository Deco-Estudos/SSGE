package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.Municipio;
import com.example.SEED.Municipio.MunicipioRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstruturaAdmService {

    @Autowired
    private EstruturaAdmRepository estruturaAdmRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EstruturaAdmDTO criarEstrutura(EstruturaAdmDTO data) {
        Municipio municipio = municipioRepository.findById(data.municipio().id())
                .orElseThrow(() -> new RuntimeException("Município não encontrado"));

        EstruturaAdm estruturaPai = null;
        if (data.estruturaPaiId() != null) {
            estruturaPai = estruturaAdmRepository.findById(data.estruturaPaiId())
                    .orElseThrow(() -> new RuntimeException("Estrutura pai não encontrada"));
        }

        EstruturaAdm estruturaAdm = EstruturaAdm.builder()
                .name(data.name())
                .tipo(data.tipo())
                .municipio(municipio)
                .ativo(data.ativo())
                .cep(data.cep())
                .estruturaPai(estruturaPai)
                .build();

        EstruturaAdm saved = estruturaAdmRepository.save(estruturaAdm);
        return toDTO(saved);
    }

    @Transactional
    public EstruturaAdmDTO atualizarEstrutura(Long id, EstruturaAdmDTO data) {
        EstruturaAdm existing = estruturaAdmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estrutura não encontrada"));

        if (data.municipio() != null) {
            Municipio municipio = municipioRepository.findById(data.municipio().id())
                    .orElseThrow(() -> new RuntimeException("Município não encontrado"));
            existing.setMunicipio(municipio);
        }

        EstruturaAdm estruturaPai = null;
        if (data.estruturaPaiId() != null) {
            estruturaPai = estruturaAdmRepository.findById(data.estruturaPaiId())
                    .orElseThrow(() -> new RuntimeException("Estrutura pai não encontrada"));
        }

        existing.setName(data.name());
        existing.setTipo(data.tipo());
        existing.setAtivo(data.ativo());
        existing.setCep(data.cep());
        existing.setEstruturaPai(estruturaPai);

        EstruturaAdm updated = estruturaAdmRepository.save(existing);
        return toDTO(updated);
    }

    @Transactional
    public void deletarEstrutura(Long id) {
        EstruturaAdm existing = estruturaAdmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estrutura não encontrada"));
        estruturaAdmRepository.delete(existing);
    }

    @Transactional(readOnly = true)
    public List<EstruturaAdmDTO> listarEstruturas() {
        return estruturaAdmRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstruturaAdmDTO> listarEstruturasAtivas() {
        return estruturaAdmRepository.findByAtivoTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<EstruturaAdmDTO> listarEstruturasDoUsuario(String email) {
        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        Set<EstruturaAdm> estruturasUnicas = new HashSet<>();

        // 1. Adiciona escolas onde o usuário é responsável por algum SETOR
        if (usuario.getSetores() != null) {
            usuario.getSetores().forEach(setor ->
                    estruturasUnicas.add(setor.getEstruturaAdm())
            );
        }

        // 2. Adiciona escolas onde o usuário é DIRETOR direto
        if (usuario.getEstruturasAdministradas() != null) {
            estruturasUnicas.addAll(usuario.getEstruturasAdministradas());
        }

        // Converte para DTO e retorna como Lista
        return estruturasUnicas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstruturaAdmDTO> listarPorTipo(TipoEstrutura tipo) {
        return estruturaAdmRepository.findByTipo(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EstruturaAdmDTO toDTO(EstruturaAdm e) {
        return new EstruturaAdmDTO(
                e.getId(),
                e.getName(),
                e.getTipo(),
                new com.example.SEED.Municipio.MunicipioDTO(
                        e.getMunicipio().getId(),
                        e.getMunicipio().getNome()
                ),
                e.getAtivo(),
                e.getCep(),
                e.getEstruturaPai() != null ? e.getEstruturaPai().getId() : null
        );
    }
}