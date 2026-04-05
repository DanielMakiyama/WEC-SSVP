package com.daniel.wec_ssvp.service;

import com.daniel.wec_ssvp.model.dto.UsuarioRequestDTO;
import com.daniel.wec_ssvp.model.entity.Usuario;
import com.daniel.wec_ssvp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CriarUsuarioService(UsuarioRepository usuarioRepository,
                               PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario executar(UsuarioRequestDTO dto) {
        usuarioRepository.findByEmail(dto.email())
                .ifPresent(u -> {
                    throw new RuntimeException("Email já cadastrado");
                });

        Usuario usuario = new Usuario(
                dto.email(),
                passwordEncoder.encode(dto.senha()),
                dto.tipoUsuario()
        );

        return usuarioRepository.save(usuario);
    }
}