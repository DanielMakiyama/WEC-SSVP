package com.daniel.wec_ssvp.dto;

public record ConferenciaResponseDTO(
        Integer id,
        String nome,
        Boolean ativo,
        Integer conselhoParticularId,
        String conselhoParticularNome// Devolveo ID do cp que pertece para o front saber a quem pertence
) {
}