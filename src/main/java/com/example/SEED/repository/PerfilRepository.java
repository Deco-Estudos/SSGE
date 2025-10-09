package com.example.SEED.repository;

import com.example.SEED.model.NomePerfil;
import com.example.SEED.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNomePerfil(NomePerfil perfil);
}
