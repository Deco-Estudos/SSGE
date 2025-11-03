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


    private ComboItemResponseDTO toResponseDTO(ComboItem comboItem) {
        Item item = comboItem.getItem();

        Classificacao classificacao = item.getClassificacao();
        ClassificacaoDTO classificacaoDTO = null;
        if (classificacao != null) {
            classificacaoDTO = new ClassificacaoDTO(
                    classificacao.getId(),
                    classificacao.getNomeClassificacao(),
                    classificacao.getDescricao()
            );
        } // Se for nula, o DTO permanecerá nulo. Ajuste conforme necessário.

        ItemDTO itemDTO = new ItemDTO(
                item.getId(),
                item.getNomeItem(),
                item.getDescricao(),
                classificacaoDTO,
                item.getTipoDado(), // Mantém tipo_dado
                item.getObrigatorio(),
                item.isAtivo()

        );

        return new ComboItemResponseDTO(
                comboItem.getId(),
                comboItem.getOrdem(),
                comboItem.getObrigatorio(),
                comboItem.getValor(), // Usa o campo 'valor' renomeado
                itemDTO
        );
    }

    @Transactional
    public ComboItemResponseDTO addItemToCombo(Long comboId, AddComboItemRequestDTO requestDTO) {

        // 1. Validar se a ordem já existe para este combo
        if (comboItemRepository.existsByComboIdAndOrdem(comboId, requestDTO.ordem())) {
            throw new IllegalStateException("A ordem '" + requestDTO.ordem() + "' já está em uso neste kit.");
        }

        // 2. Validar se a ordem é um número >= 1
        try {
            int ordemNum = Integer.parseInt(requestDTO.ordem());
            if (ordemNum < 1) {
                throw new IllegalArgumentException("A ordem deve ser um número maior ou igual a 1.");
            }
        } catch (NumberFormatException e) {
            // Se não puder ser convertido para número, lança exceção.
            // Remova ou ajuste este bloco se quiser permitir ordens não numéricas.
            throw new IllegalArgumentException("A ordem deve ser um valor numérico (Ex: '1', '2', etc.).");
        }


        // 3. Validar se o item já não está no combo (mantido)
        if (comboItemRepository.existsByComboIdAndItemId(comboId, requestDTO.itemId())) {
            throw new IllegalStateException("Este item já foi adicionado a este combo.");
        }

        // 4. Buscar as entidades principais
        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado com o ID: " + comboId));

        Item item = itemRepository.findById(requestDTO.itemId())
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com o ID: " + requestDTO.itemId()));

        // 5. Criar a nova entidade de associação
        ComboItem newComboItem = new ComboItem();
        newComboItem.setCombo(combo);
        newComboItem.setItem(item);
        newComboItem.setOrdem(requestDTO.ordem());
        newComboItem.setObrigatorio(requestDTO.obrigatorio());
        newComboItem.setValor(requestDTO.valor()); // Salva o campo 'valor' renomeado

        // 6. Salvar no banco
        ComboItem savedComboItem = comboItemRepository.save(newComboItem);

        // 7. Retornar o DTO de resposta
        return toResponseDTO(savedComboItem);
    }

    @Transactional(readOnly = true)
    public List<ComboItemResponseDTO> getItemsForCombo(Long comboId) {
        if (!comboRepository.existsById(comboId)) {
            throw new EntityNotFoundException("Combo não encontrado com o ID: " + comboId);
        }
        return comboItemRepository.findByComboIdOrderByOrdem(comboId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeItemFromCombo(Long comboItemId) {
        if (!comboItemRepository.existsById(comboItemId)) {
            throw new EntityNotFoundException("Associação Item-Combo não encontrada com o ID: " + comboItemId);
        }
        comboItemRepository.deleteById(comboItemId);
    }
}