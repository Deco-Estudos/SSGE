package com.example.SEED.Usuario;

import com.example.SEED.EstruturaAdm.EstruturaAdm; // 1. IMPORTAR
import com.example.SEED.Perfil.Perfil;
import com.example.SEED.Setor.Setor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false,unique = true)
    private String cpf;

    @Column(unique = true)
    private String telefone;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    private boolean ativo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil")
    Perfil perfil;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_setor",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_setor")
    )
    private Set<Setor> setores;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_estrutura",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_estrutura_adm")
    )
    private Set<EstruturaAdm> estruturasAdministradas;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(perfil.getAuthorityName()));
    }

    @Override
    public String getPassword() { return this.senha; }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.ativo; }

    public Usuario(String nome, String email, String senha, String cpf, String telefone, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.perfil = perfil;
        this.dataCadastro = new Date();
        this.ativo = false;
    }
}