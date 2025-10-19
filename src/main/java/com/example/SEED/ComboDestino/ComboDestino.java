package com.example.SEED.ComboDestino;

import com.example.SEED.Combo.Combo;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Setor.Setor;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "combo_destino")
public class ComboDestino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_combo", nullable = false)
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estrutura_adm", nullable = false)
    private EstruturaAdm estruturaAdm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_setor")
    private Setor setor; // opcional

    private LocalDateTime dataEnvio = LocalDateTime.now();

    private Boolean ativo = true;
}
