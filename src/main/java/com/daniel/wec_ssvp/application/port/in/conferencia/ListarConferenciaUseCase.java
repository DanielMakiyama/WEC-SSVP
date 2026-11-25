package com.daniel.wec_ssvp.application.port.in.conferencia;

import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.List;
import java.util.UUID;

/** Porta de entrada: consultar Conferências (RF007). */
public interface ListarConferenciaUseCase {

    List<Conferencia> listarTodas();

    Conferencia buscarPorId(UUID id);

    List<Conferencia> listarPorConselho(UUID conselhoId);
}
