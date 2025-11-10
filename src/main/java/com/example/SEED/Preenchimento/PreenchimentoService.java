package com.example.SEED.Preenchimento;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import com.example.SEED.Item.Item;
import com.example.SEED.Item.ItemRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Cria novos preenchimentos para um combo destino.
     */
    @Transactional
    public void salvarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {
        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId).orElse(null);
        if (comboDestino == null) return;

        // ✅ Recupera o usuário autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email).orElse(null);
        if (usuario == null) return;

        for (PreenchimentoCreateDTO dto : preenchimentosDTO) {
            Item item = itemRepository.findById(dto.itemId()).orElse(null);
            if (item == null) continue;

            Preenchimento preenchimento = Preenchimento.builder()
                    .item(item)
                    .usuario(usuario)
                    .estruturaAdm(comboDestino.getEstruturaAdm())
                    .competencia(comboDestino.getCompetencia())
                    .valor(dto.valor())
                    .observacao(dto.observacao())
                    .dataPreenchimento(new Date())
                    .build();

            preenchimentoRepository.save(preenchimento);
        }
    }

    /**
     * Atualiza preenchimentos já existentes de um combo destino.
     */
    @Transactional
    public void atualizarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {
        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId).orElse(null);
        if (comboDestino == null) return;

        // ✅ Recupera o usuário autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email).orElse(null);
        if (usuario == null) return;

        for (PreenchimentoCreateDTO dto : preenchimentosDTO) {
            Item item = itemRepository.findById(dto.itemId()).orElse(null);
            if (item == null) continue;

            Preenchimento existente = preenchimentoRepository
                    .findByEstruturaAdmAndCompetenciaAndItem(
                            comboDestino.getEstruturaAdm(),
                            comboDestino.getCompetencia(),
                            item
                    )
                    .orElse(null);

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
                        .competencia(comboDestino.getCompetencia())
                        .valor(dto.valor())
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

        List<Preenchimento> preenchimentos = preenchimentoRepository
                .findByEstruturaAdmAndCompetenciaAndUsuario(
                        comboDestino.getEstruturaAdm(),
                        comboDestino.getCompetencia(),
                        usuario
                );

        return preenchimentos.stream()
                .map(p -> new PreenchimentoResponseDTO(
                        p.getItem().getId(),
                        p.getValor(),
                        p.getObservacao()
                ))
                .toList();
    }

}
