package com.example.microblog.repository;

import com.example.microblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByDataPostagemDesc();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.curtidas")
    List<Post> findAllWithCurtidas();
}