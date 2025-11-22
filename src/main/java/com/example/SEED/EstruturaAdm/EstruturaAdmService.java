package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.Municipio;
import com.example.SEED.Municipio.MunicipioRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstruturaAdmService {

    @Autowired
    EstruturaAdmRepository estruturaAdmRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    private UserRepository userRepository; // Nova Injeção

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

    public void deletarEstrutura(Long id) {
        EstruturaAdm existing = estruturaAdmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estrutura não encontrada"));
        estruturaAdmRepository.delete(existing);
    }

    public List<EstruturaAdmDTO> listarEstruturas() {
        return estruturaAdmRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }


    public List<EstruturaAdmDTO> listarEstruturasAtivas() {
        return estruturaAdmRepository.findByAtivoTrue().stream()
                .map(this::toDTO)
                .toList();
    }


    public List<EstruturaAdmDTO> listarEstruturasDoUsuario(String email) {
        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        return usuario.getSetores().stream()
                .map(setor -> setor.getEstruturaAdm())
                .distinct() // Remove duplicatas
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EstruturaAdmDTO> listarPorTipo(TipoEstrutura tipo) {
        return estruturaAdmRepository.findByTipo(tipo).stream()
                .map(this::toDTO)
                .toList();
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