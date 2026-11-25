package com.daniel.wec_ssvp.domain.model;

import java.util.UUID;

/**
 * Entidade de domínio da Conferência, sempre vinculada a um Conselho Particular.
 */
public class Conferencia {

    private final UUID id;
    private String nome;
    private boolean ativo;
    private ConselhoParticular conselhoParticular;

    public Conferencia(UUID id, String nome, boolean ativo, ConselhoParticular conselhoParticular) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.conselhoParticular = conselhoParticular;
    }

    public static Conferencia nova(String nome, ConselhoParticular conselhoParticular) {
        return new Conferencia(null, nome, true, conselhoParticular);
    }

    public void inativar() {
        this.ativo = false;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public boolean isAtivo() { return ativo; }
    public ConselhoParticular getConselhoParticular() { return conselhoParticular; }
}
