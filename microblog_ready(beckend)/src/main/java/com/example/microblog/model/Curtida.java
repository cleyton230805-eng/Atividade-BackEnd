package com.example.microblog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Curtida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime dataCurtida = LocalDateTime.now();

    public Curtida() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public LocalDateTime getDataCurtida() { return dataCurtida; }
    public void setDataCurtida(LocalDateTime dataCurtida) { this.dataCurtida = dataCurtida; }
}