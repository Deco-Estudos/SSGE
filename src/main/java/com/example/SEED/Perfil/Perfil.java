package com.example.SEED.Perfil;

import com.example.SEED.model.NomePerfil;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome_perfil", nullable = false)
    private NomePerfil nomePerfil;

    private String descricao;

    @Column(name = "nivel_acesso")
    private int nivelAcesso;

    // Não vira coluna
    public String getAuthorityName() {
        return nomePerfil.getRole();
    }

}
