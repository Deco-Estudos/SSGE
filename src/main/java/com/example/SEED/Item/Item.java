package com.example.SEED.Item;

import com.example.SEED.Classificacao.Classificacao;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long id;

    @Column(name = "nome_item", nullable = false)
    private String nomeItem;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_classificacao")
    private Classificacao classificacao;

    @Column(name = "tipo_dado")
    private String tipoDado;

    private Boolean obrigatorio;

    private boolean ativo;
}
