package com.example.SEED.Item;

import com.example.SEED.Classificacao.Classificacao;
import com.example.SEED.Classificacao.ClassificacaoDTO;
import com.example.SEED.Classificacao.ClassificacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    private ItemDTO toDTO(Item item) {
        // Busca a classificação associada ao item
        Classificacao classificacao = item.getClassificacao();

        // Cria o DTO da classificação com os campos corretos
        ClassificacaoDTO classificacaoDTO = new ClassificacaoDTO(
                classificacao.getId(),
                classificacao.getNomeClassificacao(),
                classificacao.getDescricao()
        );

        return new ItemDTO(
                item.getId(),
                item.getNomeItem(),
                item.getDescricao(),
                classificacaoDTO,
                item.getTipoDado(),
                item.getObrigatorio(),
                item.isAtivo()
        );
    } // <-- A CHAVE FOI ADICIONADA AQUI

    @Transactional(readOnly = true)
    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemDTO findById(Long id) {
        return itemRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com o ID: " + id));
    }

    @Transactional
    public ItemDTO create(ItemDTO itemDTO) {
        // Busca a entidade Classificacao a partir do ID fornecido no DTO
        Classificacao classificacao = classificacaoRepository.findById(itemDTO.classificacaoDTO().id())
                .orElseThrow(() -> new EntityNotFoundException("Classificação não encontrada com o ID: " + itemDTO.classificacaoDTO().id()));

        Item item = new Item();
        item.setNomeItem(itemDTO.nomeItem());
        item.setDescricao(itemDTO.descricao());
        item.setClassificacao(classificacao);
        item.setTipoDado(itemDTO.tipo_dado());
        item.setObrigatorio(itemDTO.obrigatorio());
        item.setAtivo(true); // Itens são criados como ativos por padrão

        Item savedItem = itemRepository.save(item);
        return toDTO(savedItem);
    }

    @Transactional
    public ItemDTO update(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com o ID: " + id));

        Classificacao classificacao = classificacaoRepository.findById(itemDTO.classificacaoDTO().id())
                .orElseThrow(() -> new EntityNotFoundException("Classificação não encontrada com o ID: " + itemDTO.classificacaoDTO().id()));

        item.setNomeItem(itemDTO.nomeItem());
        item.setDescricao(itemDTO.descricao());
        item.setClassificacao(classificacao);
        item.setTipoDado(itemDTO.tipo_dado());
        item.setObrigatorio(itemDTO.obrigatorio());
        item.setAtivo(itemDTO.ativo());

        Item updatedItem = itemRepository.save(item);
        return toDTO(updatedItem);
    }

    @Transactional
    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item não encontrado com o ID: " + id);
        }
        itemRepository.deleteById(id);
    }
}