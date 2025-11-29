package com.example.SEED.SolicitacaoComboItem;

import com.example.SEED.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoSolicitacao tipo;

    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    private String setor;
    private String estrutura;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    private String feedbackAdm;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
}