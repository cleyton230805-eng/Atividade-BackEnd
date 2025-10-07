package com.example.microblog.dto;

import com.example.microblog.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostDTO {
    private Long id;
    private String conteudo;
    private LocalDateTime dataPostagem;
    private String nomeUsuario;
    private Long usuarioId;
    private int quantidadeCurtidas;
    private List<Long> usuariosQueCurtiram;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.conteudo = post.getConteudo();
        this.dataPostagem = post.getDataPostagem();
        this.nomeUsuario = post.getUsuario().getNome();
        this.usuarioId = post.getUsuario().getId();
        this.quantidadeCurtidas = post.getCurtidas().size();
        this.usuariosQueCurtiram = post.getCurtidas().stream()
                .map(curtida -> curtida.getUsuario().getId())
                .collect(Collectors.toList());
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getDataPostagem() { return dataPostagem; }
    public void setDataPostagem(LocalDateTime dataPostagem) { this.dataPostagem = dataPostagem; }
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public int getQuantidadeCurtidas() { return quantidadeCurtidas; }
    public void setQuantidadeCurtidas(int quantidadeCurtidas) { this.quantidadeCurtidas = quantidadeCurtidas; }
    public List<Long> getUsuariosQueCurtiram() { return usuariosQueCurtiram; }
    public void setUsuariosQueCurtiram(List<Long> usuariosQueCurtiram) { this.usuariosQueCurtiram = usuariosQueCurtiram; }
}