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
        }

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
                comboItem.getValor(), // 1. CAMPO 'VALOR' INCLUÍDO
                itemDTO
        );
    }

    @Transactional
    public ComboItemResponseDTO addItemToCombo(Long comboId, AddComboItemRequestDTO requestDTO) {


        int ordemNum;
        try {
            ordemNum = Integer.parseInt(requestDTO.ordem());
            if (ordemNum < 1) {
                // Esta linha impede números negativos ou zero
                throw new IllegalArgumentException("A ordem deve ser um número maior ou igual a 1.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A ordem deve ser um valor numérico (Ex: '1', '2', etc.).");
        }

        // 2.1 Validação de ordem repetida
        if (comboItemRepository.existsByComboIdAndOrdem(comboId, requestDTO.ordem())) {
            throw new IllegalStateException("A ordem '" + requestDTO.ordem() + "' já está em uso neste kit.");
        }


        // 3. Validar se o item já não está no combo
        if (comboItemRepository.existsByComboIdAndItemId(comboId, requestDTO.itemId())) {
            throw new IllegalStateException("Este item já foi adicionado a este combo.");
        }


        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado com o ID: " + comboId));

        Item item = itemRepository.findById(requestDTO.itemId())
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com o ID: " + requestDTO.itemId()));


        ComboItem newComboItem = new ComboItem();
        newComboItem.setCombo(combo);
        newComboItem.setItem(item);
        newComboItem.setOrdem(requestDTO.ordem());
        newComboItem.setObrigatorio(requestDTO.obrigatorio());
        newComboItem.setValor(requestDTO.valor());


        ComboItem savedComboItem = comboItemRepository.save(newComboItem);


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