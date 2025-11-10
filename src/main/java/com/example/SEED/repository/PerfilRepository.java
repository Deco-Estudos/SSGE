package com.example.SEED.repository;

import com.example.SEED.Perfil.NomePerfil;
import com.example.SEED.Perfil.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // 1. IMPORTAR LIST
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByNomePerfil(NomePerfil perfil);


    List<Perfil> findByNomePerfilNot(NomePerfil nomePerfil);
}