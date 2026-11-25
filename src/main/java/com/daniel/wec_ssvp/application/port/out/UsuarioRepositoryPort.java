package com.daniel.wec_ssvp.application.port.out;

import com.daniel.wec_ssvp.domain.model.Usuario;

import java.util.Optional;

/** Porta de saída: persistência de usuários. */
public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);
}
