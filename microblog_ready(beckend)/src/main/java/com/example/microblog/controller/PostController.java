package com.example.microblog.controller;

import com.example.microblog.dto.CriarPostDTO;
import com.example.microblog.dto.PostDTO;
import com.example.microblog.model.Curtida;
import com.example.microblog.model.Post;
import com.example.microblog.model.Usuario;
import com.example.microblog.repository.CurtidaRepository;
import com.example.microblog.repository.PostRepository;
import com.example.microblog.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CurtidaRepository curtidaRepository;

    @PostMapping
    public ResponseEntity<PostDTO> criarPost(@Valid @RequestBody CriarPostDTO criarPostDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(criarPostDTO.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = new Post();
        post.setConteudo(criarPostDTO.getConteudo());
        post.setUsuario(usuarioOpt.get());

        Post postSalvo = postRepository.save(post);
        return ResponseEntity.ok(new PostDTO(postSalvo));
    }

    @GetMapping("/cronologico")
    public ResponseEntity<List<PostDTO>> listarCronologico() {
        List<Post> posts = postRepository.findAllByOrderByDataPostagemDesc();
        List<PostDTO> postsDTO = posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postsDTO);
    }

    @GetMapping("/relevancia")
    public ResponseEntity<List<PostDTO>> listarPorRelevancia() {
        List<Post> posts = postRepository.findAllWithCurtidas();

        List<PostDTO> postsDTO = posts.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getCurtidas().size(), p1.getCurtidas().size()))
                .map(PostDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(postsDTO);
    }

    @PostMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<?> curtirPost(@PathVariable Long postId, @PathVariable Long usuarioId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (postOpt.isEmpty() || usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOpt.get();
        Usuario usuario = usuarioOpt.get();

        // Verificar se já curtiu
        if (curtidaRepository.existsByPostAndUsuario(post, usuario)) {
            return ResponseEntity.ok().build(); // Já curtido, retorna OK sem fazer nada
        }

        Curtida curtida = new Curtida();
        curtida.setPost(post);
        curtida.setUsuario(usuario);
        curtidaRepository.save(curtida);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<?> removerCurtida(@PathVariable Long postId, @PathVariable Long usuarioId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (postOpt.isEmpty() || usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOpt.get();
        Usuario usuario = usuarioOpt.get();

        Optional<Curtida> curtidaOpt = curtidaRepository.findByPostAndUsuario(post, usuario);
        if (curtidaOpt.isPresent()) {
            curtidaRepository.delete(curtidaOpt.get());
        }

        return ResponseEntity.ok().build();
    }
}