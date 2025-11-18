package com.example.SEED.Preenchimento;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Item.Item;
import com.example.SEED.Item.ItemRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PreenchimentoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;


    @Transactional
    public void salvarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {
        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId)
                .orElseThrow(() -> new RuntimeException("ComboDestino não encontrado."));

        Competencia competencia = comboDestino.getCombo().getCompetencia();
        validarCompetenciaAberta(competencia);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        for (PreenchimentoCreateDTO dto : preenchimentosDTO) {
            Item item = itemRepository.findById(dto.itemId()).orElse(null);
            if (item == null) continue;

            Preenchimento preenchimento = Preenchimento.builder()
                    .item(item)
                    .usuario(usuario)
                    .estruturaAdm(comboDestino.getEstruturaAdm())
                    .competencia(competencia)
                    .valor(dto.valor())          // Salva Valor R$
                    .quantidade(dto.quantidade()) // Salva Quantidade (NOVO)
                    .observacao(dto.observacao())
                    .dataPreenchimento(new Date())
                    .build();

            preenchimentoRepository.save(preenchimento);
        }
    }


    @Transactional
    public void atualizarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {
        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId)
                .orElseThrow(() -> new RuntimeException("ComboDestino não encontrado."));

        Competencia competencia = comboDestino.getCombo().getCompetencia();
        validarCompetenciaAberta(competencia);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        for (PreenchimentoCreateDTO dto : preenchimentosDTO) {
            Item item = itemRepository.findById(dto.itemId()).orElse(null);
            if (item == null) continue;

            Preenchimento existente = preenchimentoRepository
                    .findByEstruturaAdmAndCompetenciaAndItem(
                            comboDestino.getEstruturaAdm(),
                            competencia,
                            item
                    ).orElse(null);

            if (existente != null) {
                existente.setValor(dto.valor());           // Atualiza Valor R$
                existente.setQuantidade(dto.quantidade()); // Atualiza Quantidade (NOVO)
                existente.setObservacao(dto.observacao());
                existente.setDataPreenchimento(new Date());
                preenchimentoRepository.save(existente);
            } else {
                // Se não existir, cria um novo
                Preenchimento novo = Preenchimento.builder()
                        .item(item)
                        .usuario(usuario)
                        .estruturaAdm(comboDestino.getEstruturaAdm())
                        .competencia(competencia)
                        .valor(dto.valor())          // Salva Valor R$
                        .quantidade(dto.quantidade()) // Salva Quantidade (NOVO)
                        .observacao(dto.observacao())
                        .dataPreenchimento(new Date())
                        .build();
                preenchimentoRepository.save(novo);
            }
        }
    }


    public List<PreenchimentoResponseDTO> buscarPorComboDestino(Long comboDestinoId, String email) {
        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId)
                .orElseThrow(() -> new RuntimeException("ComboDestino não encontrado"));

        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Competencia competencia = comboDestino.getCombo().getCompetencia();

        List<Preenchimento> preenchimentos = preenchimentoRepository
                .findByEstruturaAdmAndCompetenciaAndUsuario(
                        comboDestino.getEstruturaAdm(),
                        competencia,
                        usuario
                );

        return preenchimentos.stream()
                .map(p -> new PreenchimentoResponseDTO(
                        p.getItem().getId(),
                        p.getValor(),      // Retorna Valor R$
                        p.getQuantidade(), // Retorna Quantidade (NOVO)
                        p.getObservacao()
                )).toList();
    }


    private void validarCompetenciaAberta(Competencia competencia) {
        LocalDate hoje = LocalDate.now();


        LocalDate inicio = competencia.getDataInicio().toLocalDate();
        LocalDate fim = competencia.getDataFim().toLocalDate();

        boolean dentroPeriodo = !hoje.isBefore(inicio) && !hoje.isAfter(fim);

        if (!dentroPeriodo)
            throw new RuntimeException("O período da competência está encerrado.");
    }
}