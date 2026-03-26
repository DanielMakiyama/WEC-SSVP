package com.daniel.wec_ssvp.dto;

public record ConferenciaRequestDTO(
        String nome,
        Integer conselhoParticularId // ID do conselho q ela pertence
){
}