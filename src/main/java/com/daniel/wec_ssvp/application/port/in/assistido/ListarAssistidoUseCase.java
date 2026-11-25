package com.daniel.wec_ssvp.application.port.in.assistido;

import com.daniel.wec_ssvp.domain.model.Assistido;

import java.util.List;
import java.util.UUID;

/** Porta de entrada: consultar assistidos (RF009). */
public interface ListarAssistidoUseCase {

    List<Assistido> listarTodos();

    Assistido buscarPorId(UUID id);
}
