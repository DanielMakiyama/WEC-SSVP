package com.daniel.wec_ssvp.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "conferencias")
public class ConferenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "conselho_particular_id", nullable = false)
    private ConselhoParticularEntity conselhoParticular;

    public ConferenciaEntity() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public ConselhoParticularEntity getConselhoParticular() { return conselhoParticular; }
    public void setConselhoParticular(ConselhoParticularEntity conselhoParticular) {
        this.conselhoParticular = conselhoParticular;
    }
}
