package com.daniel.wec_ssvp.model.dto.informacoesComplementares;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record SituacaoFamiliarDTO(
        @NotNull(message = "Quantidade de trabalhadores não pode ser nula")
        Integer quantidadeTrabalhadores,

        @NotNull(message = "Renda familiar não pode ser nula")
        BigDecimal rendaFamiliar,

        @NotNull(message = "Renda líquida não pode ser nula")
        BigDecimal rendaLiquida,

        @NotNull(message = "Valor do aluguel não pode ser nulo")
        BigDecimal valorAluguel,

        @NotNull(message = "Quantidade alfabetizados não pode ser nula")
        Integer quantidadeAlfabetizados,

        String situacaoMoradia
) {}
