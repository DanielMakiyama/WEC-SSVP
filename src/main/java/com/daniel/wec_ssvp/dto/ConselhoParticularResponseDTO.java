package com.daniel.wec_ssvp.dto;

import java.time.LocalDate;

public record ConselhoParticularResponseDTO(
        Integer id,
        String nome,
        String cidade,
        LocalDate dataFundacao,
        Boolean ativo
) {
}

