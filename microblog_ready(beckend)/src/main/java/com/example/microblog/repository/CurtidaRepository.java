package com.example.microblog.repository;

import com.example.microblog.model.Curtida;
import com.example.microblog.model.Post;
import com.example.microblog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    Optional<Curtida> findByPostAndUsuario(Post post, Usuario usuario);
    boolean existsByPostAndUsuario(Post post, Usuario usuario);

    Optional<Curtida> findByUsuarioAndPost(Usuario usuario, Post post);
    void deleteByUsuarioAndPost(Usuario usuario, Post post);
}