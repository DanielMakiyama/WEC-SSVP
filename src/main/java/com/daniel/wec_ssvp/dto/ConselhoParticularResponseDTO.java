package com.daniel.wec_ssvp.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ConselhoParticularResponseDTO(
        UUID id,
        String nome,
        String cidade,
        LocalDate dataFundacao,
        Boolean ativo
) {
}

