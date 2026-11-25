package com.daniel.wec_ssvp.application.port.in.assistido;

import com.daniel.wec_ssvp.domain.model.Assistido;

/** Porta de entrada: cadastrar um assistido (RF010). */
public interface CriarAssistidoUseCase {

    Assistido executar(AssistidoCommand command);
}
