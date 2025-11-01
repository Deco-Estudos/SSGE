package com.example.SEED.responsavel_setor;

import com.example.SEED.Combo.ComboDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsavel-setor")
public class ResponsavelSetorController {

    private final ResponsavelSetorService responsavelSetorService;

    public ResponsavelSetorController(ResponsavelSetorService responsavelSetorService) {
        this.responsavelSetorService = responsavelSetorService;
    }

    @GetMapping("/combos")
    public List<ComboDTO> listarCombos() {
        return responsavelSetorService.listarCombos();
    }

}
