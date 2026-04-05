package com.daniel.wec_ssvp.model.dto.cadastro;

import java.util.UUID;

public record ConselhoParticularResponseDTO(
        UUID id,
        String message
) {

    public static ConselhoParticularResponseDTO criado(UUID id, String nome) {
        return new ConselhoParticularResponseDTO(
                id,
                "Conselho Particular '" + nome + "' criado com sucesso!"
        );
    }
}

