package com.daniel.wec_ssvp.application.usecase.conferencia;

import com.daniel.wec_ssvp.application.port.in.conferencia.DeletarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.UUID;

public class DeletarConferenciaService implements DeletarConferenciaUseCase {

    private final ConferenciaRepositoryPort repository;

    public DeletarConferenciaService(ConferenciaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Conferencia executar(UUID id) {
        Conferencia conferencia = repository.buscarPorId(id)
                .orElseThrow(ConferenciaNaoEncontradaException::new);

        conferencia.inativar();

        return repository.salvar(conferencia);
    }
}
