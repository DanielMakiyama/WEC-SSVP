package com.daniel.wec_ssvp.domain.model;

import java.util.UUID;

/**
 * Entidade de domínio do Usuário do sistema.
 * Guarda apenas o hash da senha, nunca a senha em texto (RNF02).
 */
public class Usuario {

    private final UUID id;
    private String email;
    private String senhaHash;
    private TipoUsuario tipoUsuario;
    private boolean ativo;

    public Usuario(UUID id, String email, String senhaHash, TipoUsuario tipoUsuario, boolean ativo) {
        this.id = id;
        this.email = email;
        this.senhaHash = senhaHash;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
    }

    public static Usuario novo(String email, String senhaHash, TipoUsuario tipoUsuario) {
        return new Usuario(null, email, senhaHash, tipoUsuario, true);
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getSenhaHash() { return senhaHash; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public boolean isAtivo() { return ativo; }
}
