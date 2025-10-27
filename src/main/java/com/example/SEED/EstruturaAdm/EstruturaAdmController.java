package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.MunicipioDTO;
import com.example.SEED.Municipio.MunicipioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm/estruturas")
public class EstruturaAdmController {

    @Autowired
    private EstruturaAdmService service;

    @Autowired
    MunicipioRepository municipioRepository;

    @PostMapping
    public ResponseEntity<EstruturaAdmDTO> createEstrutura(@Valid @RequestBody EstruturaAdmDTO dto) {
        EstruturaAdmDTO created = service.criarEstrutura(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<EstruturaAdmDTO>> listEstruturas() {
        return ResponseEntity.ok(service.listarEstruturas());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<EstruturaAdmDTO>> listByTipo(@PathVariable TipoEstrutura tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstruturaAdmDTO> updateEstrutura(
            @PathVariable Long id,
            @Valid @RequestBody EstruturaAdmDTO dto
    ) {
        EstruturaAdmDTO updated = service.atualizarEstrutura(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstrutura(@PathVariable Long id) {
        service.deletarEstrutura(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/municipios")
    public ResponseEntity<List<MunicipioDTO>> listarMunicipios() {
        List<MunicipioDTO> municipios = municipioRepository.findAll()
                .stream()
                .map(m -> new MunicipioDTO(m.getId(), m.getNome()))
                .toList();
        return ResponseEntity.ok(municipios);
    }
}
