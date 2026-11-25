package com.daniel.wec_ssvp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Objeto de valor com os dados cadastrais de um assistido (RF010).
 * Agrupa os campos para que cadastro e edição usem a mesma estrutura.
 */
public record DadosAssistido(
        // Dados pessoais
        String nome,
        String conjuge,
        String endereco,
        LocalDate dataNascimento,
        String estadoCivil,
        String religiao,
        String profissao,

        // Situação familiar
        String situacaoMoradia,
        Integer quantidadeTrabalhadores,
        BigDecimal rendaFamiliar,
        BigDecimal rendaLiquida,
        BigDecimal valorAluguel,
        Integer quantidadeAlfabetizados,

        // Igreja e saúde
        String situacaoCatequeseCrisma,
        String participacaoIgrejaCatolica,
        String problemaSaude,
        String outrasInformacoes
) {}
