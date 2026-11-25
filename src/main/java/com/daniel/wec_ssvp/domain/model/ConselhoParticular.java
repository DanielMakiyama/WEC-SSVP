package com.daniel.wec_ssvp.domain.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidade de domínio do Conselho Particular.
 * Não conhece JPA, Spring ou HTTP: só os dados e as regras.
 */
public class ConselhoParticular {

    private final UUID id;
    private String nome;
    private String cidade;
    private LocalDate dataFundacao;
    private boolean ativo;

    public ConselhoParticular(UUID id, String nome, String cidade, LocalDate dataFundacao, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.dataFundacao = dataFundacao;
        this.ativo = ativo;
    }

    /** Cria um novo Conselho, ainda sem id (o id é gerado ao salvar). */
    public static ConselhoParticular novo(String nome, String cidade, LocalDate dataFundacao) {
        return new ConselhoParticular(null, nome, cidade, dataFundacao, true);
    }

    /** Exclusão lógica: o registro continua no banco, mas inativo. */
    public void inativar() {
        this.ativo = false;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public LocalDate getDataFundacao() { return dataFundacao; }
    public boolean isAtivo() { return ativo; }
}
