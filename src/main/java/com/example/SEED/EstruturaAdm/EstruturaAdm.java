package com.example.SEED.EstruturaAdm;

import com.example.SEED.Municipio.Municipio;
import com.example.SEED.Usuario.Usuario; // Importar
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set; // Importar

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "estrutura_adm")
public class EstruturaAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estrutura_adm")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEstrutura tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    @Column(nullable = false)
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estrutura_pai")
    private EstruturaAdm estruturaPai;

    private String cep;

    @OneToMany(mappedBy = "estruturaAdm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.example.SEED.Setor.Setor> setores;

    @ManyToMany(mappedBy = "estruturasAdministradas")
    private Set<Usuario> diretores;
}