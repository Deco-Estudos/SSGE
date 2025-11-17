package com.example.SEED.Competencia;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competencia")
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_competencia")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private String mes;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CompetenciaStatus competenciaStatus;
}
