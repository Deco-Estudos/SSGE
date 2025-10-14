package com.example.SEED.repository;

import com.example.SEED.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    /*A moça da vídeo aula recomendou usar UserDetails pois é gerenciado pelo SpringSecurity,
    Mas o chatGPT disse que usar Optional era melhor, ent n sei ainda qual usar
    */
    UserDetails findByEmail(String email);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByAtivoFalse(); //O Jpa consegue entender o False por ser um boolean
}
