package com.daniel.wec_ssvp.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record BuscaComumDTO (
        UUID id,
        String nome,
        LocalDate dataNascimento,
        Boolean status,
        String conferencia
) {}
