package com.example.SEED.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario_perfil_estrutura")
public class UsuarioPerfilEstrutura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_perfil_estrutura")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //LAZY = Só carrega quando necessário (recomendado pra relações N:1 ou 1:N grandes) | EAGER = Carrega tudo junto (útil se sempre precisar das entidades relacionadas).
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estrutura_adm", nullable = false)
    private EstruturaAdm estruturaAdm;

    @Column(nullable = false)
    private Date dataAssociacao;

    @Column(nullable = false)
    private boolean ativo;

}
