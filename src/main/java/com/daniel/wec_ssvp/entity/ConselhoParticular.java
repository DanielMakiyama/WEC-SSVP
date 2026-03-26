package com.daniel.wec_ssvp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "conselhos_particulares")
public class ConselhoParticular {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(name = "data_fundacao")
    private LocalDate dataFundacao;

    @Column(nullable = false)
    private Boolean ativo = true;

    public ConselhoParticular() {
    }

    public ConselhoParticular(String nome, String cidade, LocalDate dataFundacao, Boolean ativo) {
        this.nome = nome;
        this.cidade = cidade;
        this.dataFundacao = dataFundacao;
        this.ativo = ativo;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public LocalDate getDataFundacao() { return dataFundacao; }
    public void setDataFundacao(LocalDate dataFundacao) { this.dataFundacao = dataFundacao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}

