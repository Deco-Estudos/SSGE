package com.example.SEED.controller;

import com.example.SEED.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @GetMapping
    public String getAllUsers(){ //Só pra testar o Spring Security
        return "Pode não man";
    }
}
