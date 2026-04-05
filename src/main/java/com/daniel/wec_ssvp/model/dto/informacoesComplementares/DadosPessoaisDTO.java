package com.daniel.wec_ssvp.model.dto.informacoesComplementares;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record DadosPessoaisDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String conjuge,

        @NotBlank(message = "Endereço é obrigatório")
        String endereco,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento inválida")
        LocalDate dataNascimento,

        String estadoCivil,
        String religiao,
        String profissao
) {}
