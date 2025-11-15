package com.example.SEED.Preenchimento;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Item.Item;
import com.example.SEED.Item.ItemRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PreenchimentoService {

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    // ============================================================
    // 🟦 MÉTODO 1 — SALVAR PRIMEIRO PREENCHIMENTO
    // ============================================================
    @Transactional
    public void salvarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {

        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId)
                .orElseThrow(() -> new RuntimeException("ComboDestino não encontrado."));

        // Agora pega a competência: comboDestino → combo → competencia
        Competencia competencia = comboDestino.getCombo().getCompetencia();
        validarCompetenciaAberta(competencia);

        // usuário logado
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
                    .valor(dto.valor())
                    .observacao(dto.observacao())
                    .dataPreenchimento(new Date())
                    .build();

            preenchimentoRepository.save(preenchimento);
        }
    }


    // ============================================================
    // 🟦 MÉTODO 2 — ATUALIZAR PREENCHIMENTO EXISTENTE
    // ============================================================
    @Transactional
    public void atualizarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {

        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId)
                .orElseThrow(() -> new RuntimeException("ComboDestino não encontrado."));

        // Agora pega a competência: comboDestino → combo → competencia
        Competencia competencia = comboDestino.getCombo().getCompetencia();
        validarCompetenciaAberta(competencia);

        // usuário logado
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
                existente.setValor(dto.valor());
                existente.setObservacao(dto.observacao());
                existente.setDataPreenchimento(new Date());
                preenchimentoRepository.save(existente);

            } else {
                Preenchimento novo = Preenchimento.builder()
                        .item(item)
                        .usuario(usuario)
                        .estruturaAdm(comboDestino.getEstruturaAdm())
                        .competencia(competencia)
                        .valor(dto.valor())
                        .observacao(dto.observacao())
                        .dataPreenchimento(new Date())
                        .build();

                preenchimentoRepository.save(novo);
            }
        }
    }


    // ============================================================
    // 🟦 MÉTODO 3 — BUSCAR PREENCHIMENTOS DO COMBO DESTINO
    // ============================================================
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
                        p.getValor(),
                        p.getObservacao()
                )).toList();
    }


    // ============================================================
    // 🔒 MÉTODO INTERNO — VALIDAR SE COMPETÊNCIA ESTÁ ABERTA
    // ============================================================
    private void validarCompetenciaAberta(Competencia competencia) {

        LocalDateTime agora = LocalDateTime.now();

        boolean dentroPeriodo =
                !agora.isBefore(competencia.getDataInicio()) &&
                        !agora.isAfter(competencia.getDataFim());

        if (!dentroPeriodo)
            throw new RuntimeException("O período da competência está encerrado.");
    }
}
