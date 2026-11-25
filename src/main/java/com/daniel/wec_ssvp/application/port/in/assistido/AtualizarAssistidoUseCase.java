package com.daniel.wec_ssvp.application.port.in.assistido;

import com.daniel.wec_ssvp.domain.model.Assistido;

import java.util.UUID;

/** Porta de entrada: editar o cadastro de um assistido (RF012). */
public interface AtualizarAssistidoUseCase {

    Assistido executar(UUID id, AssistidoCommand command);
}
