package com.example.SEED.Uf;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "uf")
public class Uf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uf")
    private Long id;

    @Column(nullable = false)
    private String sigla;

    @Column(nullable = false)
    private String nome;

}
