package com.example.SEED.ComboItem;

import com.example.SEED.Classificacao.Classificacao;
import com.example.SEED.Classificacao.ClassificacaoDTO;
import com.example.SEED.Combo.Combo;
import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.Item.Item;
import com.example.SEED.Item.ItemDTO;
import com.example.SEED.Item.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComboItemService {

    @Autowired
    private ComboItemRepository comboItemRepository;

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ItemRepository itemRepository;

    // Método de conversão para a resposta da API
    private ComboItemResponseDTO toResponseDTO(ComboItem comboItem) {
        Item item = comboItem.getItem();
        Classificacao classificacao = item.getClassificacao();

        ClassificacaoDTO classificacaoDTO = new ClassificacaoDTO(
                classificacao.getId(),
                classificacao.getNomeClassificacao(),
                classificacao.getDescricao()
        );

        ItemDTO itemDTO = new ItemDTO(
                item.getId(),
                item.getNomeItem(),
                item.getDescricao(),
                classificacaoDTO,
                item.getTipoDado(),
                item.getObrigatorio(),
                item.isAtivo()
        );

        return new ComboItemResponseDTO(
                comboItem.getId(),
                comboItem.getOrdem(),
                comboItem.getObrigatorio(),
                itemDTO
        );
    }

    @Transactional
    public ComboItemResponseDTO addItemToCombo(Long comboId, AddComboItemRequestDTO requestDTO) {
        // 1. Validar se o item já não está no combo para evitar duplicatas
        if (comboItemRepository.existsByComboIdAndItemId(comboId, requestDTO.itemId())) {
            throw new IllegalStateException("Este item já foi adicionado a este combo.");
        }

        // 2. Buscar as entidades principais (Combo e Item)
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado com o ID: " + comboId));

        Item item = itemRepository.findById(requestDTO.itemId())
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com o ID: " + requestDTO.itemId()));

        // 3. Criar a nova entidade de associação
        ComboItem newComboItem = new ComboItem();
        newComboItem.setCombo(combo);
        newComboItem.setItem(item);
        newComboItem.setOrdem(requestDTO.ordem());
        newComboItem.setObrigatorio(requestDTO.obrigatorio());

        // 4. Salvar no banco
        ComboItem savedComboItem = comboItemRepository.save(newComboItem);

        // 5. Retornar o DTO de resposta
        return toResponseDTO(savedComboItem);
    }

    @Transactional(readOnly = true)
    public List<ComboItemResponseDTO> getItemsForCombo(Long comboId) {
        // Valida se o combo existe antes de buscar os itens
        if (!comboRepository.existsById(comboId)) {
            throw new EntityNotFoundException("Combo não encontrado com o ID: " + comboId);
        }

        // Usa o método customizado do repositório para buscar e ordenar
        return comboItemRepository.findByComboIdOrderByOrdem(comboId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeItemFromCombo(Long comboItemId) {
        // O ID aqui é o da própria tabela combo_item
        if (!comboItemRepository.existsById(comboItemId)) {
            throw new EntityNotFoundException("Associação Item-Combo não encontrada com o ID: " + comboItemId);
        }
        comboItemRepository.deleteById(comboItemId);
    }
}