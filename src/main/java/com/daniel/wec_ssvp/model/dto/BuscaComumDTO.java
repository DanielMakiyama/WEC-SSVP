package com.daniel.wec_ssvp.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record BuscaComumDTO (
        UUID id,
        String nome,
        LocalDate dataNascimento,
        Boolean status,
        String conferencia
) {}
