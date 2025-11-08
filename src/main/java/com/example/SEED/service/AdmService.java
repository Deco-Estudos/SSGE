package com.example.SEED.service;

import com.example.SEED.ADM.AtualizarUsuarioDTO;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private SetorRepository setorRepository;

    public List<UsuarioDTO> listarUsers(){
        List<Usuario> usuariosPendentes = userRepository.findByAtivoFalse();
        List<UsuarioDTO> dtos = usuariosPendentes.stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getCpf(),
                        u.getTelefone(),
                        u.getPerfil() != null ? u.getPerfil().getNomePerfil() : null,
                        u.getDataCadastro(),
                        u.isAtivo()
                ))
                .collect(Collectors.toList());
        return dtos;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosUsuarios() {
        return userRepository.findAll().stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getCpf(),
                        u.getTelefone(),
                        u.getPerfil() != null ? u.getPerfil().getNomePerfil() : null,
                        u.getDataCadastro(),
                        u.isAtivo()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizarUsuario(Long usuarioId, AtualizarUsuarioDTO dto) {
        Usuario usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        if (dto.perfilId() != null) {
            Perfil novoPerfil = perfilRepository.findById(dto.perfilId())
                    .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado com ID: " + dto.perfilId()));
            usuario.setPerfil(novoPerfil);
        }

        if (dto.setorIds() != null) {
            Set<Setor> novosSetores = new HashSet<>(setorRepository.findAllById(dto.setorIds()));

            if (novosSetores.size() != dto.setorIds().size()) {
                throw new EntityNotFoundException("Um ou mais Setores não foram encontrados.");
            }
            usuario.setSetores(novosSetores);
        }

        userRepository.save(usuario);
    }

    public String aprovarUser(Long id){
        Optional<Usuario> usuario = userRepository.findById(id);
        if(usuario.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }
        Usuario usuarioEncontrado = usuario.get();

        usuarioEncontrado.setAtivo(true);
        userRepository.save(usuarioEncontrado);

        return "Usuário aprovado com sucesso";
    }

    public String reprovarUser(Long id){
        Optional<Usuario> usuario = userRepository.findById(id);
        if(usuario.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }
        Usuario usuarioEncontrado = usuario.get();

        userRepository.delete(usuarioEncontrado);

        return "Usuário removido com sucesso";
    }
}