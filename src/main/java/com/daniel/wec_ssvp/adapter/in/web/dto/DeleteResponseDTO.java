package com.daniel.wec_ssvp.adapter.in.web.dto;


import java.util.UUID;

public record DeleteResponseDTO(
        UUID id,
        String nome,
        Boolean status,
        String message

) {

    public static DeleteResponseDTO criado (UUID id, String nome, Boolean status, String message) {
        return new DeleteResponseDTO(
                id,
                nome,
                status,
                "Requisição para deletar '" + nome + "' foi concluida com sucesso!"
        );
    }
}
