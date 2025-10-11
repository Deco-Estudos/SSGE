package com.example.SEED.service;

import com.example.SEED.dto.RegisterDTO;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    public Boolean passwordValidation(String password){
        if(password.length() < 8 || password.length() > 20){
            throw new RuntimeException("A senha deve conter entre 8 e 20 caracteres.");
        }

        boolean hasSpecialChar = false;
        boolean hasLetter = false;
        boolean hasNumber = false;

        for (char c : password.toCharArray()){
            if (Character.isWhitespace(c)){
                throw new RuntimeException("Não pode haver espaços na senha");
            }
            else if(Character.isLetter(c)){
                hasLetter = true;
            }
            else if(Character.isDigit(c)){
                hasNumber = true;
            }
            else{
                hasSpecialChar = true;
            }
        }
        if (hasLetter && hasNumber && hasSpecialChar) {
            return true;
        }
        throw new RuntimeException("A senha deve conter pelo menos uma letra, um número e um caractere especial.");
    }
}
