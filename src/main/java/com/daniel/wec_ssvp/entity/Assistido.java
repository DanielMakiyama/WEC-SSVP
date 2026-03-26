package com.daniel.wec_ssvp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "assistidos")
public class Assistido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100) // Não obrigatório
    private String conjuge;

    @Column(length = 150)
    private String endereco;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "estado_civil", length = 50)
    private String estadoCivil;

    @Column(length = 50)
    private String religiao;

    @Column(name = "situacao_moradia", length = 50)
    private String situacaoMoradia;

    @Column(length = 100)
    private String profissao;

    @Column(name = "qtd_trabalham_familia")
    private Integer quantidadeTrabalhadores;

    // precision = 10 (total de dígitos), scale = 2 (casas decimais)
    @Column(name = "renda_familiar", precision = 10, scale = 2)
    private BigDecimal rendaFamiliar;

    @Column(name = "renda_liquida", precision = 10, scale = 2)
    private BigDecimal rendaLiquida;

    @Column(name = "valor_aluguel", precision = 10, scale = 2)
    private BigDecimal valorAluguel;

    @Column(name = "qtd_alfabetizados")
    private Integer quantidadeAlfabetizados;

    @Column(name = "catequese_crisma", length = 200)
    private String situacaoCatequeseCrisma;

    @Column(name = "participacao_igreja", length = 200)
    private String participacaoIgrejaCatolica;

    // columnDefinition = "TEXT" permite textos muito grandes, ideal para descrições de saúde
    @Column(name = "problema_saude", columnDefinition = "TEXT")
    private String problemaSaude;

    @Column(name = "outras_informacoes", columnDefinition = "TEXT")
    private String outrasInformacoes;


    public Assistido() {
    }

    public Assistido(String nome, String conjuge, String endereco, LocalDate dataNascimento,
                     String estadoCivil, String religiao, String situacaoMoradia, String profissao,
                     Integer quantidadeTrabalhadores, BigDecimal rendaFamiliar, BigDecimal rendaLiquida,
                     BigDecimal valorAluguel, Integer quantidadeAlfabetizados, String situacaoCatequeseCrisma,
                     String participacaoIgrejaCatolica, String problemaSaude, String outrasInformacoes) {

        this.nome = nome;

        this.conjuge = conjuge;

        this.endereco = endereco;

        this.dataNascimento = dataNascimento;

        this.estadoCivil = estadoCivil;

        this.religiao = religiao;

        this.situacaoMoradia = situacaoMoradia;

        this.profissao = profissao;

        this.quantidadeTrabalhadores = quantidadeTrabalhadores;

        this.rendaFamiliar = rendaFamiliar;

        this.rendaLiquida = rendaLiquida;

        this.valorAluguel = valorAluguel;

        this.quantidadeAlfabetizados = quantidadeAlfabetizados;

        this.situacaoCatequeseCrisma = situacaoCatequeseCrisma;

        this.participacaoIgrejaCatolica = participacaoIgrejaCatolica;

        this.problemaSaude = problemaSaude;

        this.outrasInformacoes = outrasInformacoes;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConjuge() {
        return conjuge;
    }
    public void setConjuge(String conjuge) {
        this.conjuge = conjuge;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getReligiao() {
        return religiao;
    }
    public void setReligiao(String religiao) {
        this.religiao = religiao;
    }

    public String getSituacaoMoradia() {
        return situacaoMoradia;
    }
    public void setSituacaoMoradia(String situacaoMoradia) {
        this.situacaoMoradia = situacaoMoradia;
    }

    public String getProfissao() {
        return profissao;
    }
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Integer getQuantidadeTrabalhadores() {
        return quantidadeTrabalhadores;
    }
    public void setQuantidadeTrabalhadores(Integer quantidadeTrabalhadores) {
        this.quantidadeTrabalhadores = quantidadeTrabalhadores;
    }

    public BigDecimal getRendaFamiliar() {
        return rendaFamiliar;
    }
    public void setRendaFamiliar(BigDecimal rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
    }

    public BigDecimal getRendaLiquida() {
        return rendaLiquida;
    }
    public void setRendaLiquida(BigDecimal rendaLiquida) {
        this.rendaLiquida = rendaLiquida;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }
    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public Integer getQuantidadeAlfabetizados() {
        return quantidadeAlfabetizados;
    }
    public void setQuantidadeAlfabetizados(Integer quantidadeAlfabetizados) {
        this.quantidadeAlfabetizados = quantidadeAlfabetizados;
    }

    public String getSituacaoCatequeseCrisma() {
        return situacaoCatequeseCrisma;
    }
    public void setSituacaoCatequeseCrisma(String situacaoCatequeseCrisma) {
        this.situacaoCatequeseCrisma = situacaoCatequeseCrisma;
    }

    public String getParticipacaoIgrejaCatolica() {
        return participacaoIgrejaCatolica;
    }
    public void setParticipacaoIgrejaCatolica(String participacaoIgrejaCatolica) {
        this.participacaoIgrejaCatolica = participacaoIgrejaCatolica;
    }

    public String getProblemaSaude() {
        return problemaSaude;
    }
    public void setProblemaSaude(String problemaSaude) {
        this.problemaSaude = problemaSaude;
    }

    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }
    public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }



@ManyToOne
@JoinColumn(name = "conferencia_id", nullable = false)
private Conferencia conferencia;

public Conferencia getConferencia() {
    return conferencia;
}

public void setConferencia(Conferencia conferencia) {
    this.conferencia = conferencia;
}
}