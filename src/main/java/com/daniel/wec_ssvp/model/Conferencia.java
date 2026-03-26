package com.daniel.wec_ssvp.model;

import jakarta.persistence.*;

@Entity
@Table(name="conferencias")
public class Conferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "conselho_particular_id", nullable = false)
    private ConselhoParticular conselhoParticular;

    public Conferencia(){
    }

    public Conferencia(String nome, Boolean ativo, ConselhoParticular conselhoParticular) {
        this.nome = nome;
        this.ativo = ativo;
        this.conselhoParticular = conselhoParticular;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public ConselhoParticular getConselhoParticular() { return conselhoParticular; }
    public void setConselhoParticular(ConselhoParticular conselhoParticular) { this.conselhoParticular = conselhoParticular; }
}
