package com.daniel.wec_ssvp.model.dto.cadastro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ConselhoParticularRequestDTO(

        @NotBlank(message = "Nome da conferencia é obrigatório")
        String nome,

        @NotBlank(message = "Nome da cidade é obrigatório")
        String cidade,

        @NotNull(message = "Data de fundaçaõ é obrigatório é obrigatório")
        LocalDate dataFundacao
) {
}