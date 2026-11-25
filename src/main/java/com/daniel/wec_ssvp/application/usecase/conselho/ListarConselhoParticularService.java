package com.daniel.wec_ssvp.application.usecase.conselho;

import com.daniel.wec_ssvp.application.port.in.conselho.ListarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

import java.util.List;
import java.util.UUID;

public class ListarConselhoParticularService implements ListarConselhoParticularUseCase {

    private final ConselhoParticularRepositoryPort repository;

    public ListarConselhoParticularService(ConselhoParticularRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<ConselhoParticular> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public ConselhoParticular buscarPorId(UUID id) {
        return repository.buscarPorId(id)
                .orElseThrow(ConselhoParticularNaoEncontradoException::new);
    }
}
