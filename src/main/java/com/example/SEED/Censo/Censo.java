package com.example.SEED.Censo;

import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "censo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_estrutura_adm", "id_competencia"})
        // Garante que não tenhamos dois registros para a mesma escola no mesmo mês
})
public class Censo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estrutura_adm", nullable = false)
    private EstruturaAdm estruturaAdm;

    @ManyToOne
    @JoinColumn(name = "id_competencia", nullable = false)
    private Competencia competencia;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Integer quantidadeAlunos;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPreenchimento;
}