package com.daniel.wec_ssvp.application.port.in.assistido;

import com.daniel.wec_ssvp.domain.model.Assistido;

import java.util.UUID;

/** Porta de entrada: excluir (inativar) um assistido (RF013). */
public interface DeletarAssistidoUseCase {

    Assistido executar(UUID id);
}
