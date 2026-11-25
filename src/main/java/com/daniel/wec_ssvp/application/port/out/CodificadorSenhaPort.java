package com.daniel.wec_ssvp.application.port.out;

/** Porta de saída: criptografia de senhas (RNF02). Implementada com BCrypt. */
public interface CodificadorSenhaPort {

    String codificar(String senha);

    boolean confere(String senha, String senhaHash);
}
