package com.daniel.wec_ssvp.model.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BuscaPorIdDTO (
        UUID id,
        String nome,
        String conjuge,
        String endereco,
        LocalDate dataNascimento,
        String estadoCivil,
        String religiao,
        String profissao,
        String situacaoMoradia,
        Integer quantidadeTrabalhadores,
        BigDecimal rendaFamiliar,
        BigDecimal rendaLiquida,
        BigDecimal valorAluguel,
        Integer quantidadeAlfabetizados,
        String situacaoCatequeseCrisma,
        String participacaoIgrejaCatolica,
        String problemaSaude,
        String outrasInformacoes,
        Boolean status,
        String conferencia
) {}
