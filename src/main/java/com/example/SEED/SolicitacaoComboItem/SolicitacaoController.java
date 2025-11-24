package com.example.SEED.SolicitacaoComboItem;

import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorDTO;
import com.example.SEED.Setor.SetorRepository;
import com.example.SEED.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService service;

    @Autowired
    SetorRepository setorRepository;

    @PostMapping("/responsavel-setor/criar")
    @PreAuthorize("hasAuthority('ROLE_RESPONSAVEL_SETOR')")
    public SolicitacaoResponseDTO criar(@RequestBody SolicitacaoCreateDTO dto,
                                        @AuthenticationPrincipal Usuario usuario) {

        dto = new SolicitacaoCreateDTO(
                dto.tipo(),
                dto.nome(),
                dto.descricao(),
                usuario.getId(),
                dto.setor(),
                dto.estrutura()
        );

        return service.criar(dto);
    }

    // ADM lista todas
    @GetMapping("/adm")
    public List<SolicitacaoResponseDTO> listarTodas() {
        return service.listarTodas();
    }

    // ADM aprovar
    @PutMapping("/adm/aprovar/{id}")
    public SolicitacaoResponseDTO aprovar(
            @PathVariable Long id,
            @RequestBody SolicitacaoRespostaDTO dto
    ) {
        return service.aprovar(id, dto);
    }

    // ADM rejeitar
    @PutMapping("/adm/rejeitar/{id}")
    public SolicitacaoResponseDTO rejeitar(
            @PathVariable Long id,
            @RequestBody SolicitacaoRespostaDTO dto
    ) {
        return service.rejeitar(id, dto);
    }

    @GetMapping("/responsavel-setor/me")
    @PreAuthorize("hasAuthority('ROLE_RESPONSAVEL_SETOR')")
    public List<SolicitacaoResponseDTO> listarMinhasSolicitacoes(
            @AuthenticationPrincipal Usuario usuario
    ) {
        return service.listarPorUsuario(usuario.getId());
    }

    @GetMapping("/responsavel-setor/setores")
    @PreAuthorize("hasAuthority('ROLE_RESPONSAVEL_SETOR')")
    public List<SetorDTO> meusSetores(@AuthenticationPrincipal Usuario usuario) {
        List<Setor> setores = setorRepository.findByResponsaveisId(usuario.getId());

        List<SetorDTO> listaDTO = setores.stream()
                .map(setor -> new SetorDTO(
                        setor.getId(),
                        setor.getNome(),
                        setor.getDescricao(),
                        setor.getEstruturaAdm().getId()
                ))
                .toList();

        return listaDTO;
    }
}
