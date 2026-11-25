package com.daniel.wec_ssvp.application.port.in.assistido;

import com.daniel.wec_ssvp.domain.model.DadosAssistido;

import java.util.UUID;

/** Dados de entrada comuns ao cadastro e à edição de assistidos. */
public record AssistidoCommand(DadosAssistido dados, UUID conferenciaId) {}
