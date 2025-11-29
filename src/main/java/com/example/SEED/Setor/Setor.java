package com.example.SEED.Setor;

import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Usuario.Usuario; // 1. IMPORTADO
import jakarta.persistence.*;
import lombok.*;

import java.util.Set; // 2. IMPORTADO

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "setor")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_setor")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estrutura_adm", nullable = false)
    private EstruturaAdm estruturaAdm;

    private String descricao;


    @ManyToMany(mappedBy = "setores")
    private Set<Usuario> responsaveis;

}