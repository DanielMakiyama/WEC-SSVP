package com.daniel.wec_ssvp.application.usecase.conferencia;

import com.daniel.wec_ssvp.application.port.in.conferencia.ListarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.List;
import java.util.UUID;

public class ListarConferenciaService implements ListarConferenciaUseCase {

    private final ConferenciaRepositoryPort repository;

    public ListarConferenciaService(ConferenciaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Conferencia> listarTodas() {
        return repository.listarTodas();
    }

    @Override
    public Conferencia buscarPorId(UUID id) {
        return repository.buscarPorId(id)
                .orElseThrow(ConferenciaNaoEncontradaException::new);
    }

    @Override
    public List<Conferencia> listarPorConselho(UUID conselhoId) {
        return repository.listarPorConselho(conselhoId);
    }
}
