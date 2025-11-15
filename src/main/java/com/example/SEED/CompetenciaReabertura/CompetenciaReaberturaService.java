package com.example.SEED.CompetenciaReabertura;

import com.example.SEED.Combo.Combo;
import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompetenciaReaberturaService {

    private final CompetenciaReaberturaRepository repository;
    private final ComboRepository comboRepository;
    private final UserRepository userRepository;

    public CompetenciaReaberturaService(
            CompetenciaReaberturaRepository repository,
            ComboRepository comboRepository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.comboRepository = comboRepository;
        this.userRepository = userRepository;
    }

    // RESPONSÁVEL SOLICITA REABERTURA
    public void solicitarReabertura(CompetenciaReaberturaCreateDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario solicitante = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Combo combo = comboRepository.findById(dto.comboId())
                .orElseThrow(() -> new RuntimeException("Combo não encontrado"));

        CompetenciaReabertura r = CompetenciaReabertura.builder()
                .competencia(dto.competencia())
                .combo(combo)
                .solicitante(solicitante)
                .status(StatusReabertura.PENDENTE)
                .dataSolicitacao(new Date())
                .build();

        repository.save(r);
    }

    // ADM APROVA
    public void aprovar(CompetenciaReaberturaAprovacaoDTO dto) {

        CompetenciaReabertura r = repository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario adm = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        r.setNovaDataLimite(dto.novaDataLimite());
        r.setAdministrador(adm);
        r.setStatus(StatusReabertura.APROVADO);
        r.setDataResposta(new Date());

        repository.save(r);
    }

    // ADM NEGA
    public void negar(Long id) {

        CompetenciaReabertura r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario adm = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        r.setAdministrador(adm);
        r.setStatus(StatusReabertura.NEGADO);
        r.setDataResposta(new Date());

        repository.save(r);
    }
}
