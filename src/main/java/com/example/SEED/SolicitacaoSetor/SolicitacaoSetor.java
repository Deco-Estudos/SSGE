package com.example.SEED.SolicitacaoSetor;

import com.example.SEED.Setor.Setor;
import com.example.SEED.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "solicitacao_setor")
public class SolicitacaoSetor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // Usuário que está pedindo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_setor", nullable = false)
    private Setor setor; // Setor que ele quer entrar

    @Column(nullable = false)
    private String justificativa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SolicitacaoSetorStatus status;

    @CreationTimestamp
    @Column(name = "data_solicitacao", nullable = false, updatable = false)
    private LocalDateTime dataSolicitacao;
}