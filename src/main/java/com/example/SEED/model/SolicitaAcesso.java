package com.example.SEED.model;

import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Perfil.Perfil;
import  com.example.SEED.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "solicita_acesso")
public class SolicitaAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitacao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estrutura_adm")
    private EstruturaAdm estruturaAdm;

    @Column(name = "data_solicitacao", nullable = false)
    private Date dataSolicitacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SolicitacaoStatus solicitacaoStatus;

}
