package com.example.SEED.ComboItem;

import com.example.SEED.Combo.Combo;
import com.example.SEED.Item.Item;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "combo_item")
public class ComboItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_combo_item")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_combo", nullable = false)
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @Column(nullable = false)
    private String ordem;

    private Boolean obrigatorio;

    private Integer Valor;
}
