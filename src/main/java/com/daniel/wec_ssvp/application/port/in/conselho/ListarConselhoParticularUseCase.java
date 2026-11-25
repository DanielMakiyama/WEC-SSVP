package com.daniel.wec_ssvp.application.port.in.conselho;

import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

import java.util.List;
import java.util.UUID;

/** Porta de entrada: consultar Conselhos Particulares (RF005). */
public interface ListarConselhoParticularUseCase {

    List<ConselhoParticular> listarTodos();

    ConselhoParticular buscarPorId(UUID id);
}
