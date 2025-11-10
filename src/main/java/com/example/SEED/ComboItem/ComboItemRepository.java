package com.example.SEED.ComboItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboItemRepository extends JpaRepository<ComboItem, Long> {

    /**
     * Encontra todas as associações ComboItem para um Combo específico,
     * ordenando pelo campo 'ordem'.
     * @param comboId O ID do Combo a ser pesquisado.
     * @return Uma lista de associações ComboItem.
     */
    List<ComboItem> findByComboIdOrderByOrdem(Long comboId);

    /**
     * Verifica se já existe uma associação entre um combo e um item específicos.
     * Útil para evitar duplicatas.
     * @param comboId O ID do Combo.
     * @param itemId O ID do Item.
     * @return true se a associação já existe, false caso contrário.
     */
    boolean existsByComboIdAndItemId(Long comboId, Long itemId);

    List<ComboItem> findByComboId(Long comboId);
}