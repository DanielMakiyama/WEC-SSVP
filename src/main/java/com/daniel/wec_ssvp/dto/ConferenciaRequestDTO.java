package com.daniel.wec_ssvp.dto;

import java.util.UUID;

public record ConferenciaRequestDTO(
        String nome,
        UUID conselhoParticularId // ID do conselho q ela pertence
){
}