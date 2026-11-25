package com.daniel.wec_ssvp.application.port.out;

import com.daniel.wec_ssvp.domain.model.Assistido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Porta de saída: persistência de assistidos. */
public interface AssistidoRepositoryPort {

    Assistido salvar(Assistido assistido);

    Optional<Assistido> buscarPorId(UUID id);

    List<Assistido> listarTodos();
}
