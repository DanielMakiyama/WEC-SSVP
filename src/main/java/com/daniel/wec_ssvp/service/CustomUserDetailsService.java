package com.daniel.wec_ssvp.service;

import com.daniel.wec_ssvp.model.entity.Usuario;
import com.daniel.wec_ssvp.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getAtivo(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(usuario.getTipoUsuario().name()))
        );
    }
}
