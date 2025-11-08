package com.example.SEED.SolicitacaoSetor;

import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacaoSetorService {

    @Autowired
    private SolicitacaoSetorRepository solicitacaoSetorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SetorRepository setorRepository;

    /**
     * Chamado pelo USUÁRIO para criar um novo pedido.
     */
    @Transactional
    public void createSolicitacao(CreateSolicitacaoSetorDTO dto) {
        String usuarioEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = userRepository.findUsuarioByEmail(usuarioEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + usuarioEmail));

        Setor setor = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado: " + dto.setorId()));

        SolicitacaoSetor novaSolicitacao = SolicitacaoSetor.builder()
                .usuario(usuario)
                .setor(setor)
                .justificativa(dto.justificativa())
                .status(SolicitacaoSetorStatus.PENDENTE)
                .build();

        solicitacaoSetorRepository.save(novaSolicitacao);
    }



    /**
     * Chamado pelo ADM para ver a lista de pedidos pendentes.
     */
    @Transactional(readOnly = true)
    public List<SolicitacaoSetorDTO> listarSolicitacoesPendentes() {
        return solicitacaoSetorRepository.findByStatus(SolicitacaoSetorStatus.PENDENTE)
                .stream()
                .map(SolicitacaoSetorDTO::new) // Usa o construtor do DTO
                .collect(Collectors.toList());
    }

    /**
     * Chamado pelo ADM para APROVAR um pedido.
     */
    @Transactional
    public void aprovarSolicitacao(Long solicitacaoId) {
        SolicitacaoSetor solicitacao = solicitacaoSetorRepository.findById(solicitacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada: " + solicitacaoId));

        if (solicitacao.getStatus() != SolicitacaoSetorStatus.PENDENTE) {
            throw new IllegalStateException("Esta solicitação não está mais pendente.");
        }


        solicitacao.setStatus(SolicitacaoSetorStatus.APROVADO);
        solicitacaoSetorRepository.save(solicitacao);


        Usuario usuario = solicitacao.getUsuario();
        Setor setor = solicitacao.getSetor();

        usuario.getSetores().add(setor);
        userRepository.save(usuario);
    }


    @Transactional
    public void reprovarSolicitacao(Long solicitacaoId) {
        SolicitacaoSetor solicitacao = solicitacaoSetorRepository.findById(solicitacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada: " + solicitacaoId));

        if (solicitacao.getStatus() != SolicitacaoSetorStatus.PENDENTE) {
            throw new IllegalStateException("Esta solicitação não está mais pendente.");
        }


        solicitacao.setStatus(SolicitacaoSetorStatus.REPROVADO);
        solicitacaoSetorRepository.save(solicitacao);
    }
}