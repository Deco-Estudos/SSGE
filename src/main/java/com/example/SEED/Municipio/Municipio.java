package com.example.SEED.Municipio;

import com.example.SEED.Uf.Uf;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "municipio")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uf_id", nullable = false)
    private Uf uf;

    @Column(name = "cod_ibge", nullable = false)
    private String codeIbge;

}
