package com.daniel.wec_ssvp.adapter.out.security;

import com.daniel.wec_ssvp.application.port.out.CodificadorSenhaPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Adaptador de saída: criptografia de senhas com o BCrypt do Spring Security (RNF02). */
@Component
public class BCryptCodificadorSenhaAdapter implements CodificadorSenhaPort {

    private final PasswordEncoder passwordEncoder;

    public BCryptCodificadorSenhaAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String codificar(String senha) {
        return passwordEncoder.encode(senha);
    }

    @Override
    public boolean confere(String senha, String senhaHash) {
        return passwordEncoder.matches(senha, senhaHash);
    }
}
