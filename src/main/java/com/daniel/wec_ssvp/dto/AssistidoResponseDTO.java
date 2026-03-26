package com.daniel.wec_ssvp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public record AssistidoResponseDTO(
        UUID id,
        String nome,
        String conjuge,
        String endereco,
        LocalDate dataNascimento,
        String estadoCivil,
        String religiao,
        String situacaoMoradia,
        String profissao,
        Integer quantidadeTrabalhadores,
        BigDecimal rendaFamiliar,
        BigDecimal rendaLiquida,
        BigDecimal valorAluguel,
        Integer quantidadeAlfabetizados,
        String situacaoCatequeseCrisma,
        String participacaoIgrejaCatolica,
        String problemaSaude,
        String outrasInformacoes,
        UUID conferenciaId,
        String conferenciaNome
){
}
