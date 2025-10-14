package com.example.SEED.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.SEED.dto.UsuarioDTO;
import com.example.SEED.model.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdmService {
    @Autowired
    UserRepository userRepository;

    public List<UsuarioDTO> listarUsers(){
        List<Usuario> usuariosPendentes = userRepository.findByAtivoFalse();
        List<UsuarioDTO> dtos = usuariosPendentes.stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getCpf(),
                        u.getTelefone(),
                        u.getPerfil().getNomePerfil(),
                        u.getDataCadastro(),
                        u.isAtivo()
                ))
                .collect(Collectors.toList());
        return dtos;
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
