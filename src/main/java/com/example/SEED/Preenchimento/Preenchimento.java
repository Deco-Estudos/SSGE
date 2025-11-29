package com.example.SEED.Preenchimento;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Item.Item;
import com.example.SEED.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "preenchimento")
public class Preenchimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // Quem preencheu

    @ManyToOne
    @JoinColumn(name = "id_estrutura_adm", nullable = false)
    private EstruturaAdm estruturaAdm; // Para qual escola

    @ManyToOne
    @JoinColumn(name = "id_competencia", nullable = false)
    private Competencia competencia; // Em qual período

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;


    private Integer quantidade;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPreenchimento;
}