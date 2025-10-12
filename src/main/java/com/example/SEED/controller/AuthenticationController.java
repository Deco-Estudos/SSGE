package com.example.SEED.controller;

import com.example.SEED.dto.AuthencicationDTO;
import com.example.SEED.dto.LoginResponseDTO;
import com.example.SEED.dto.RegisterDTO;
import com.example.SEED.infra.security.TokenService;
import com.example.SEED.model.NomePerfil;
import com.example.SEED.Perfil.Perfil;
import  com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.PerfilRepository;
import com.example.SEED.repository.UserRepository;
import com.example.SEED.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    PerfilRepository perfilRepository;
    @Autowired
    RegisterService registerService;


    @PostMapping("/login")
    @Operation(summary = "Login", description = "Essa realiza o login de um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel realizar o login")
    })
    public ResponseEntity login(@RequestBody @Valid AuthencicationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar", description = "Essa realiza o Registro de um usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro efetuado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel realizar o Registro")
    })
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        try {
            registerService.passwordValidation(data.senha());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String encryptedPassword = passwordEncoder.encode(data.senha());

        Perfil perfil = perfilRepository.findByNomePerfil(data.nomePerfil())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        if(perfil.getNomePerfil() == NomePerfil.ADM){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Não é possível solicitar perfil ADM via registro");
        }

        Usuario novoUsuario = new Usuario(data.nome(), data.email(), encryptedPassword, data.cpf(), perfil); // falta colocar role
        novoUsuario.setAtivo(true);
        userRepository.save(novoUsuario);
        System.out.println("Perfil do novo usuário: " + novoUsuario.getPerfil());


        return ResponseEntity.ok().build();

    }

}
