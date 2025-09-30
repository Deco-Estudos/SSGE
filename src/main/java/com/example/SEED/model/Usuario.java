package com.example.SEED.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")

public class Usuario implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String telefone;

    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER) //MappedBy usuário pois em UsuarioPerfilEstrutura tem a FK de usuario
    private List<UsuarioPerfilEstrutura> perfisEstruturas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfisEstruturas.stream()
                .map(UsuarioPerfilEstrutura::getPerfil)
                .map(Perfil::getAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
