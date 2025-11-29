package com.example.SEED.Classificacao;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classificacao")
public class Classificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classificacao")
    private Long id;

    @Column(name = "nome_classificacao", nullable = false)
    private String nomeClassificacao;

    @Column(nullable = false)
    private String descricao;
}
