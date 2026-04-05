package com.daniel.wec_ssvp.model.dto;

import com.daniel.wec_ssvp.model.entity.TipoReuniao;
import com.daniel.wec_ssvp.model.entity.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @NotNull(message = "Tipo de usuário é obrigatório")
        TipoUsuario tipoUsuario
) {}
