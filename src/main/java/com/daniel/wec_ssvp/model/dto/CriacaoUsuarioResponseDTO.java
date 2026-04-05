package com.daniel.wec_ssvp.model.dto;

import com.daniel.wec_ssvp.model.entity.TipoUsuario;

import java.util.UUID;

public record CriacaoUsuarioResponseDTO (

    UUID id,
    String message,
    TipoUsuario tipoUsuario
) {

        public static CriacaoUsuarioResponseDTO criado(UUID id, String nome, TipoUsuario tipoUsuario) {
            return new CriacaoUsuarioResponseDTO(
                    id,
                    "O " + tipoUsuario + " '" + nome + "' foi criado com sucesso!",
                    tipoUsuario
            );
        }
}
