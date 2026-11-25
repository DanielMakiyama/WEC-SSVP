package com.daniel.wec_ssvp.application.port.in.conferencia;

import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.UUID;

/** Porta de entrada: cadastrar uma Conferência em um Conselho Particular. */
public interface CriarConferenciaUseCase {

    Conferencia executar(Command command);

    record Command(String nome, UUID conselhoParticularId) {}
}
