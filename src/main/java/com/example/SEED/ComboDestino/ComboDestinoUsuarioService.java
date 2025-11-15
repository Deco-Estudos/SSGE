package com.example.SEED.ComboDestino;

import com.example.SEED.Classificacao.ClassificacaoDTO;
import com.example.SEED.Combo.Combo;
import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.ComboItem.ComboItemRepository;
import com.example.SEED.Item.ItemDTO;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public List<ComboDestinoUsuarioDTO> listarCombosDoUsuario() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + email));

        List<Long> idsSetores = usuario.getSetores()
                .stream()
                .map(s -> s.getId())
                .toList();

        List<ComboDestino> combos = comboDestinoRepository.findBySetorIdInAndAtivoTrue(idsSetores);

        System.out.println(">>> findBySetorIdInAndAtivoTrue retornou size=" + combos.size());
        combos.forEach(cd -> System.out.println(
                "   cd.id=" + cd.getId()
                        + " combo.id=" + (cd.getCombo() != null ? cd.getCombo().getId() : "null")
                        + " setor.id=" + (cd.getSetor() != null ? cd.getSetor().getId() : "null")
                        + " ativo=" + cd.getAtivo()
        ));

        return combos.stream()
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

    public List<ComboDestinoUsuarioDTO> listarCombosPorSetor(Long setorId) {

        List<ComboDestino> combos = comboDestinoRepository.findBySetorIdAndAtivoTrue(setorId);

        return combos.stream()
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



    public List<ItemDTO> listarItensDoCombo(Long comboId) {
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado: " + comboId));

        // Busca todos os ComboItems do combo
        var comboItens = comboItemRepository.findByComboId(combo.getId());

        // Converte os itens para DTOs
        return comboItens.stream()
                .map(comboItem -> comboItem.getItem())
                .filter(item -> item.isAtivo()) // apenas itens ativos
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
