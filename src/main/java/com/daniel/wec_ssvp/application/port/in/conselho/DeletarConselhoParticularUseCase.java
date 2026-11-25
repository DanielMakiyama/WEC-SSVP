package com.daniel.wec_ssvp.application.port.in.conselho;

import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

import java.util.UUID;

/** Porta de entrada: excluir (inativar) um Conselho Particular. */
public interface DeletarConselhoParticularUseCase {

    ConselhoParticular executar(UUID id);
}
