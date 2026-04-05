package com.daniel.wec_ssvp.model.dto.cadastro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ConferenciaRequestDTO(
        @NotBlank(message = "Nome da conferencia é obrigatório")
        String nome,

        @NotNull(message = "ID da conferencia é obrigatório")
        UUID conselhoParticularId // ID do conselho q ela pertence
){
}