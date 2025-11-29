package com.example.SEED.Rh;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import com.example.SEED.ComboItem.ComboItem;
import com.example.SEED.ComboItem.ComboItemRepository;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaStatus;
import com.example.SEED.Item.Item;
import com.example.SEED.Item.ItemRepository;
import com.example.SEED.Preenchimento.Preenchimento;
import com.example.SEED.Preenchimento.PreenchimentoRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RhService {

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;
    @Autowired
    private ComboItemRepository comboItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PreenchimentoRepository preenchimentoRepository;
    @Autowired
    private UserRepository userRepository;

    // 1. Listar todos os CARGOS (Itens) que estão em combos de RH ativos
    @Transactional(readOnly = true)
    public List<RhItemDTO> listarItensRhDisponiveis() {
        // Busca todos os combos destinos
        List<ComboDestino> destinos = comboDestinoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();
        Set<Item> itensUnicos = new HashSet<>();

        for (ComboDestino cd : destinos) {
            // Filtra se está ativo e na competência aberta
            if (!cd.getAtivo()) continue;
            Competencia c = cd.getCombo().getCompetencia();
            if (c == null || c.getCompetenciaStatus() != CompetenciaStatus.ABERTO) continue;
            if (agora.isBefore(c.getDataInicio()) || agora.isAfter(c.getDataFim())) continue;

            // Pega os itens desse combo
            List<ComboItem> comboItens = comboItemRepository.findByComboId(cd.getCombo().getId());

            for (ComboItem ci : comboItens) {
                // Filtra apenas itens da categoria RH
                if ("Recursos Humanos".equalsIgnoreCase(ci.getItem().getClassificacao().getNomeClassificacao())) {
                    itensUnicos.add(ci.getItem());
                }
            }
        }

        return itensUnicos.stream()
                .map(i -> new RhItemDTO(i.getId(), i.getNomeItem(), i.getDescricao()))
                .sorted(Comparator.comparing(RhItemDTO::nome))
                .collect(Collectors.toList());
    }

    // 2. Dado um ITEM, listar todas as ESCOLAS (ComboDestinos) que precisam dele
    @Transactional(readOnly = true)
    public List<RhEscolaEntryDTO> listarEscolasPorItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        List<ComboDestino> destinos = comboDestinoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();
        List<RhEscolaEntryDTO> resultado = new ArrayList<>();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email).orElseThrow();

        for (ComboDestino cd : destinos) {
            if (!cd.getAtivo()) continue;
            Competencia c = cd.getCombo().getCompetencia();
            if (c == null || c.getCompetenciaStatus() != CompetenciaStatus.ABERTO) continue;
            if (agora.isBefore(c.getDataInicio()) || agora.isAfter(c.getDataFim())) continue;

            // Verifica se este combo destino CONTÉM o item selecionado
            boolean contemItem = comboItemRepository.existsByComboIdAndItemId(cd.getCombo().getId(), itemId);

            if (contemItem) {
                // Busca se já existe preenchimento para preencher os inputs
                Preenchimento p = preenchimentoRepository.findByEstruturaAdmAndCompetenciaAndItem(
                        cd.getEstruturaAdm(), c, item
                ).orElse(null);

                resultado.add(new RhEscolaEntryDTO(
                        cd.getId(),
                        cd.getEstruturaAdm().getName(),
                        p != null ? p.getQuantidade() : null,
                        p != null ? p.getValor() : null
                ));
            }
        }

        // Ordena pelo nome da escola
        resultado.sort(Comparator.comparing(RhEscolaEntryDTO::nomeEstrutura));
        return resultado;
    }

    // 3. Salvar em Lote (Várias escolas para um item)
    @Transactional
    public void salvarLote(Long itemId, List<RhPreenchimentoLoteDTO> dados) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = userRepository.findUsuarioByEmail(email).orElseThrow();
        LocalDateTime agora = LocalDateTime.now();

        for (RhPreenchimentoLoteDTO dto : dados) {
            ComboDestino cd = comboDestinoRepository.findById(dto.comboDestinoId())
                    .orElseThrow(() -> new EntityNotFoundException("ComboDestino não encontrado: " + dto.comboDestinoId()));

            // Upsert (Atualiza ou Cria)
            Preenchimento p = preenchimentoRepository.findByEstruturaAdmAndCompetenciaAndItem(
                    cd.getEstruturaAdm(), cd.getCombo().getCompetencia(), item
            ).orElse(new Preenchimento());

            if (p.getId() == null) { // Se é novo
                p.setItem(item);
                p.setUsuario(usuario);
                p.setEstruturaAdm(cd.getEstruturaAdm());
                p.setCompetencia(cd.getCombo().getCompetencia());
            }

            p.setQuantidade(dto.quantidade());
            p.setValor(dto.valor());
            p.setDataPreenchimento(new Date());

            preenchimentoRepository.save(p);
        }
    }
}