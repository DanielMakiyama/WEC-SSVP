package com.daniel.wec_ssvp.application.port.in.usuario;

/** Porta de entrada: login por e-mail e senha, devolvendo o token de acesso (RF001). */
public interface AutenticarUsuarioUseCase {

    String autenticar(String email, String senha);
}
