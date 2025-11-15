package com.example.SEED.CompetenciaReabertura;

import com.example.SEED.Combo.Combo;
import com.example.SEED.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaReabertura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Competência reaberta (ex: "01/2025")
    private String competencia;

    // Nova data limite escolhida pelo ADM
    @Temporal(TemporalType.DATE)
    private Date novaDataLimite;

    // Status da solicitação
    @Enumerated(EnumType.STRING)
    private StatusReabertura status;

    @ManyToOne
    private Combo combo; // qual combo essa reabertura afeta

    @ManyToOne
    private Usuario solicitante; // responsável setor solicitante

    @ManyToOne
    private Usuario administrador; // quem aprovou

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataResposta;
}
