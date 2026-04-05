package com.daniel.wec_ssvp.model.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoUsuario tipoUsuario;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Usuario() {
    }

    public Usuario(String email, String senha, TipoUsuario tipoUsuario) {
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.ativo = true;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
