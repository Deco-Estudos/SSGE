package com.example.SEED.ComboDestino;

import com.example.SEED.Classificacao.ClassificacaoDTO;
import com.example.SEED.Combo.Combo;
import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.ComboItem.ComboItem; // Importar
import com.example.SEED.ComboItem.ComboItemRepository;
import com.example.SEED.Item.ItemDTO;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.util.List;

@Service
public class ComboDestinoUsuarioService {

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ComboItemRepository comboItemRepository;

    @Transactional // Necessário para ler os itens (Lazy)
    public List<ComboDestinoUsuarioDTO> listarCombosDoUsuario() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + email));

        List<Long> idsSetores = usuario.getSetores().stream()
                .map(s -> s.getId())
                .toList();

        List<ComboDestino> combos = comboDestinoRepository.findBySetorIdInAndAtivoTrue(idsSetores);

        return combos.stream()
                // --- FILTRO ANTI-RH ---
                .filter(cd -> {
                    // Busca os itens do combo
                    List<ComboItem> itens = comboItemRepository.findByComboId(cd.getCombo().getId());

                    // Verifica se ALGUM item é de "Recursos Humanos"
                    boolean ehDeRh = itens.stream().anyMatch(ci ->
                            "Recursos Humanos".equalsIgnoreCase(ci.getItem().getClassificacao().getNomeClassificacao())
                    );

                    // Só deixa passar se NÃO for de RH
                    return !ehDeRh;
                })
                // -----------------------
                .map(cd -> new ComboDestinoUsuarioDTO(
                        cd.getId(),
                        cd.getCombo() != null ? cd.getCombo().getId() : null,
                        cd.getCombo() != null ? cd.getCombo().getNomeCombo() : null,
                        cd.getSetor() != null ? cd.getSetor().getId() : null,
                        cd.getSetor() != null ? cd.getSetor().getNome() : null,
                        cd.getDataEnvio(),
                        cd.getCombo() != null && cd.getCombo().getCompetencia() != null
                                ? cd.getCombo().getCompetencia().getDataFim().toLocalDate()
                                : null
                ))
                .toList();
    }

    // ... (Manter o método listarItensDoCombo e listarCombosPorSetor iguais) ...
    // Se precisar que eu reenvie os outros métodos, me avise.

    public List<ComboDestinoUsuarioDTO> listarCombosPorSetor(Long setorId) {
        List<ComboDestino> combos = comboDestinoRepository.findBySetorIdAndAtivoTrue(setorId);
        return combos.stream()
                .filter(cd -> {
                    List<ComboItem> itens = comboItemRepository.findByComboId(cd.getCombo().getId());
                    boolean ehDeRh = itens.stream().anyMatch(ci -> "Recursos Humanos".equalsIgnoreCase(ci.getItem().getClassificacao().getNomeClassificacao()));
                    return !ehDeRh;
                })
                .map(cd -> new ComboDestinoUsuarioDTO(
                        cd.getId(),
                        cd.getCombo().getId(),
                        cd.getCombo().getNomeCombo(),
                        cd.getSetor().getId(),
                        cd.getSetor().getNome(),
                        cd.getDataEnvio(),
                        cd.getCombo().getCompetencia().getDataFim().toLocalDate()
                )).toList();
    }

    public List<ItemDTO> listarItensDoCombo(Long comboId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado: " + comboId));

        var comboItens = comboItemRepository.findByComboId(combo.getId());

        return comboItens.stream()
                .map(comboItem -> comboItem.getItem())
                .filter(item -> item.isAtivo())
                .map(item -> new ItemDTO(
                        item.getId(),
                        item.getNomeItem(),
                        item.getDescricao(),
                        item.getClassificacao() != null
                                ? new ClassificacaoDTO(
                                item.getClassificacao().getId(),
                                item.getClassificacao().getNomeClassificacao(),
                                item.getClassificacao().getDescricao()
                        )
                                : null,
                        item.getTipoDado(),
                        item.getObrigatorio(),
                        item.isAtivo()

                ))
                .toList();
    }
}