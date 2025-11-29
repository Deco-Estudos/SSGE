package com.example.SEED.Combo;


import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.Competencia.Competencia;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "combo")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_combo")
    private Long id;

    @Column(name = "nome_combo", nullable = false)
    private String nomeCombo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo;

    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_competencia", nullable = false)
    private Competencia competencia;


    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComboDestino> destinos;

}
