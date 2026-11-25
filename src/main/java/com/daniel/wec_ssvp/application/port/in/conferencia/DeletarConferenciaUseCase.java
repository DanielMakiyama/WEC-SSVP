package com.daniel.wec_ssvp.application.port.in.conferencia;

import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.UUID;

/** Porta de entrada: excluir (inativar) uma Conferência. */
public interface DeletarConferenciaUseCase {

    Conferencia executar(UUID id);
}
