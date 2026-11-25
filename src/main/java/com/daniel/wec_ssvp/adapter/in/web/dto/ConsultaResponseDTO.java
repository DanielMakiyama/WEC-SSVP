package com.daniel.wec_ssvp.adapter.in.web.dto;

import java.util.UUID;

public record ConsultaResponseDTO(
        UUID id,
        String nome,
        Boolean status
) {}

