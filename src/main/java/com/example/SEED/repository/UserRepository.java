package com.example.SEED.repository;

import com.example.SEED.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {


    UserDetails findByEmail(String email);

    Optional<Usuario> findUsuarioByEmail(String email);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByAtivoFalse();
}