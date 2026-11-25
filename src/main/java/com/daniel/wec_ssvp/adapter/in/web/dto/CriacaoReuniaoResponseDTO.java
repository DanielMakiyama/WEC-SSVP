package com.daniel.wec_ssvp.adapter.in.web.dto;

import java.util.UUID;

public record CriacaoReuniaoResponseDTO(
        UUID id,
        String message,
        TipoReuniao tipoReuniao
) {

    public static CriacaoReuniaoResponseDTO criado(UUID id, String nome, TipoReuniao tipoReuniao) {
        return new CriacaoReuniaoResponseDTO(
                id,
                "O " + tipoReuniao + " '" + nome + "' foi criado com sucesso!",
                tipoReuniao
        );
    }
}

