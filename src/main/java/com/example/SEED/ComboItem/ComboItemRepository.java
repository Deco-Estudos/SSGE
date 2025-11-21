package com.example.SEED.ComboItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboItemRepository extends JpaRepository<ComboItem, Long> {


    List<ComboItem> findByComboIdOrderByOrdem(Long comboId);


    boolean existsByComboIdAndItemId(Long comboId, Long itemId);

    List<ComboItem> findByComboId(Long comboId);

    boolean existsByComboIdAndOrdem(Long comboId, String ordem);

    List<ComboItem> findByItemId(Long itemId);

}