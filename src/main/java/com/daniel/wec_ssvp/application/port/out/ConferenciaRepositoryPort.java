package com.daniel.wec_ssvp.application.port.out;

import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Porta de saída: persistência de Conferências. */
public interface ConferenciaRepositoryPort {

    Conferencia salvar(Conferencia conferencia);

    Optional<Conferencia> buscarPorId(UUID id);

    Optional<Conferencia> buscarPorNomeEConselho(String nome, UUID conselhoParticularId);

    List<Conferencia> listarTodas();

    List<Conferencia> listarPorConselho(UUID conselhoParticularId);
}
