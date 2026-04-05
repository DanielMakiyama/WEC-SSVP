package com.daniel.wec_ssvp.model.dto.cadastro;

import java.util.UUID;

public record ConferenciaResponseDTO(
        UUID id,
        String message
) {

    public static ConferenciaResponseDTO criado(UUID id, String nome) {
        return new ConferenciaResponseDTO(
                id,
                "Conferencia '" + nome + "' criado com sucesso!"
        );
    }
}