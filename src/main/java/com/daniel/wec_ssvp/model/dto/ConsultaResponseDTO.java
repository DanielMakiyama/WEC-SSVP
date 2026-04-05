package com.daniel.wec_ssvp.model.dto;

import java.util.UUID;

public record ConsultaResponseDTO(
        UUID id,
        String nome,
        Boolean status
) {}

