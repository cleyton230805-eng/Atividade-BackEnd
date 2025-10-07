package com.example.microblog.controller;

import com.example.microblog.dto.UsuarioDTO;
import com.example.microblog.model.Usuario;
import com.example.microblog.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody Usuario usuario) {
        // Verificar se username já existe
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            return ResponseEntity.badRequest().body("Username já está em uso");
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioDTO(usuarioSalvo));
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuario.getId() == null || !usuarioRepository.existsById(usuario.getId())) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioDTO(usuarioAtualizado));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }
}