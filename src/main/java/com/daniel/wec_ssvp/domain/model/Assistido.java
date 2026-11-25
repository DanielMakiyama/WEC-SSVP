package com.daniel.wec_ssvp.domain.model;

import java.util.UUID;

/**
 * Entidade de domínio do Assistido, vinculado a uma Conferência.
 */
public class Assistido {

    private final UUID id;
    private DadosAssistido dados;
    private Conferencia conferencia;
    private boolean ativo;

    public Assistido(UUID id, DadosAssistido dados, Conferencia conferencia, boolean ativo) {
        this.id = id;
        this.dados = dados;
        this.conferencia = conferencia;
        this.ativo = ativo;
    }

    public static Assistido novo(DadosAssistido dados, Conferencia conferencia) {
        return new Assistido(null, dados, conferencia, true);
    }

    /** Substitui os dados cadastrais e a Conferência do assistido (RF012). */
    public void atualizar(DadosAssistido novosDados, Conferencia novaConferencia) {
        this.dados = novosDados;
        this.conferencia = novaConferencia;
    }

    public void inativar() {
        this.ativo = false;
    }

    public UUID getId() { return id; }
    public DadosAssistido getDados() { return dados; }
    public String getNome() { return dados.nome(); }
    public Conferencia getConferencia() { return conferencia; }
    public boolean isAtivo() { return ativo; }
}
