package com.daniel.wec_ssvp.dto;

import java.time.LocalDate;

public record ConselhoParticularRequestDTO(
        String nome,
        String cidade,
        LocalDate dataFundacao
) {
}