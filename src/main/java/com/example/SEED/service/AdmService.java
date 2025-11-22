package com.example.SEED.service;

import com.example.SEED.ADM.AtualizarUsuarioDTO;
import com.example.SEED.EstruturaAdm.EstruturaAdm; // Importar
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository; // Importar
import com.example.SEED.Perfil.Perfil;
import com.example.SEED.repository.PerfilRepository;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.Usuario.UsuarioDTO;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdmService {

    @Autowired UserRepository userRepository;
    @Autowired private PerfilRepository perfilRepository;
    @Autowired private SetorRepository setorRepository;
    @Autowired private EstruturaAdmRepository estruturaRepository; // Nova Injeção

    public List<UsuarioDTO> listarUsers(){
        List<Usuario> usuariosPendentes = userRepository.findByAtivoFalse();
        return usuariosPendentes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosUsuarios() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizarUsuario(Long usuarioId, AtualizarUsuarioDTO dto) {
        Usuario usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        // 1. Atualiza Perfil
        if (dto.perfilId() != null) {
            Perfil novoPerfil = perfilRepository.findById(dto.perfilId())
                    .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado com ID: " + dto.perfilId()));
            usuario.setPerfil(novoPerfil);
        }

        // 2. Atualiza Setores (Para Responsável de Setor)
        if (dto.setorIds() != null) {
            Set<Setor> novosSetores = new HashSet<>(setorRepository.findAllById(dto.setorIds()));
            usuario.setSetores(novosSetores);
        }

        // 3. Atualiza Estruturas (Para Diretor) -- NOVO BLOCO
        if (dto.estruturaIds() != null) {
            Set<EstruturaAdm> novasEstruturas = new HashSet<>(estruturaRepository.findAllById(dto.estruturaIds()));
            usuario.setEstruturasAdministradas(novasEstruturas);
        }

        userRepository.save(usuario);
    }

    // ... métodos aprovar/reprovar ...
    public String aprovarUser(Long id){
        Optional<Usuario> usuario = userRepository.findById(id);
        if(usuario.isEmpty()) throw new RuntimeException("Usuário não encontrado");
        Usuario u = usuario.get();
        u.setAtivo(true);
        userRepository.save(u);
        return "Usuário aprovado";
    }

    public String reprovarUser(Long id){
        Optional<Usuario> usuario = userRepository.findById(id);
        if(usuario.isEmpty()) throw new RuntimeException("Usuário não encontrado");
        userRepository.delete(usuario.get());
        return "Usuário removido";
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(), u.getNome(), u.getEmail(), u.getCpf(), u.getTelefone(),
                u.getPerfil() != null ? u.getPerfil().getNomePerfil() : null,
                u.getDataCadastro(), u.isAtivo()
        );
    }
}