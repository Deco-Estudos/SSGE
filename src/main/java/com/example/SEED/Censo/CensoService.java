package com.example.SEED.Censo;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaRepository;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CensoService {

    @Autowired
    private CensoRepository censoRepository;
    @Autowired
    private EstruturaAdmRepository estruturaRepository;
    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void salvarCenso(CensoDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        EstruturaAdm estrutura = estruturaRepository.findById(dto.estruturaId())
                .orElseThrow(() -> new EntityNotFoundException("Estrutura não encontrada"));

        Competencia competencia = competenciaRepository.findById(dto.competenciaId())
                .orElseThrow(() -> new EntityNotFoundException("Competência não encontrada"));

        // Verifica se já existe registro para essa escola neste mês
        Censo censo = censoRepository.findByEstruturaAdmAndCompetencia(estrutura, competencia)
                .orElse(new Censo()); // Se não existir, cria um novo objeto vazio

        // Atualiza os dados
        censo.setEstruturaAdm(estrutura);
        censo.setCompetencia(competencia);
        censo.setQuantidadeAlunos(dto.quantidadeAlunos());
        censo.setUsuario(usuario);
        censo.setDataPreenchimento(new Date());

        censoRepository.save(censo);
    }

    // Metodo para buscar o valor atual
    public Integer buscarQuantidadeAlunos(Long estruturaId, Long competenciaId) {
        EstruturaAdm estrutura = estruturaRepository.findById(estruturaId).orElseThrow();
        Competencia competencia = competenciaRepository.findById(competenciaId).orElseThrow();

        return censoRepository.findByEstruturaAdmAndCompetencia(estrutura, competencia)
                .map(Censo::getQuantidadeAlunos)
                .orElse(null); // Retorna null se ainda não foi informado
    }
}