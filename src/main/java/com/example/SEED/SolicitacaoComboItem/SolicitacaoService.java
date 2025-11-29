package com.example.SEED.SolicitacaoComboItem;

import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private EstruturaAdmRepository estruturaRepository; // se não tiver, cria

    // Criar solicitação
    public SolicitacaoResponseDTO criar(SolicitacaoCreateDTO dto) {

        Usuario solicitante = userRepo.findById(dto.solicitanteId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Solicitacao s = Solicitacao.builder()
                .tipo(dto.tipo())
                .nome(dto.nome())
                .descricao(dto.descricao())
                .solicitante(solicitante)
                .setor(dto.setor())
                .estrutura(dto.estrutura())
                .status(StatusSolicitacao.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .build();;

        return toResponse(repo.save(s));
    }

    // ADM vê todas
    public List<SolicitacaoResponseDTO> listarTodas() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SolicitacaoResponseDTO> listarPorUsuario(Long usuarioId) {
        List<Solicitacao> lista = repo.findBySolicitanteId(usuarioId);

        return lista.stream()
                .map(this::toResponse)
                .toList();
    }


    // Aprovar
    public SolicitacaoResponseDTO aprovar(Long id, SolicitacaoRespostaDTO dto) {
        Solicitacao s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        s.setStatus(StatusSolicitacao.APROVADA);
        s.setFeedbackAdm(dto.feedbackAdm());
        s.setDataConclusao(LocalDateTime.now());

        return toResponse(repo.save(s));
    }

    // Rejeitar
    public SolicitacaoResponseDTO rejeitar(Long id, SolicitacaoRespostaDTO dto) {
        Solicitacao s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        s.setStatus(StatusSolicitacao.REJEITADA);
        s.setFeedbackAdm(dto.feedbackAdm());
        s.setDataConclusao(LocalDateTime.now());

        return toResponse(repo.save(s));
    }

    // conversor final

    private String buscarNomeSetor(String setorIdStr) {
        if (setorIdStr == null) return null;
        try {
            Long setorId = Long.parseLong(setorIdStr);
            return setorRepository.findById(setorId)
                    .map(Setor::getNome)
                    .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String buscarNomeEstrutura(String estruturaIdStr) {
        if (estruturaIdStr == null) return null;
        try {
            Long estruturaId = Long.parseLong(estruturaIdStr);
            return estruturaRepository.findById(estruturaId)
                    .map(EstruturaAdm::getName)
                    .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private SolicitacaoResponseDTO toResponse(Solicitacao s) {
        return new SolicitacaoResponseDTO(
                s.getId(),
                s.getTipo(),
                s.getNome(),
                s.getDescricao(),
                s.getSolicitante().getId(),
                s.getSolicitante().getNome(),
                s.getSetor(),
                s.getSetor() != null ? buscarNomeSetor(s.getSetor()) : null, // ou s.getSetorObj().getNome()
                s.getEstrutura(),
                s.getEstrutura() != null ? buscarNomeEstrutura(s.getEstrutura()) : null,
                s.getStatus(),
                s.getFeedbackAdm(),
                s.getDataCriacao(),
                s.getDataConclusao()
        );
    }
}
