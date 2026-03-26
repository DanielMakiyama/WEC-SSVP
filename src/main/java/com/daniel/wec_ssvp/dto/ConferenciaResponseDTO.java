package com.daniel.wec_ssvp.dto;

import java.util.UUID;

public record ConferenciaResponseDTO(
        UUID id,
        String nome,
        Boolean ativo,
        UUID conselhoParticularId,
        String conselhoParticularNome// Devolveo ID do cp que pertece para o front saber a quem pertence
) {
}