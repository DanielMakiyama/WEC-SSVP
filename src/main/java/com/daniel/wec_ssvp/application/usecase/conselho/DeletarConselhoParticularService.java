package com.daniel.wec_ssvp.application.usecase.conselho;

import com.daniel.wec_ssvp.application.port.in.conselho.DeletarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

import java.util.UUID;

public class DeletarConselhoParticularService implements DeletarConselhoParticularUseCase {

    private final ConselhoParticularRepositoryPort repository;

    public DeletarConselhoParticularService(ConselhoParticularRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ConselhoParticular executar(UUID id) {
        ConselhoParticular conselho = repository.buscarPorId(id)
                .orElseThrow(ConselhoParticularNaoEncontradoException::new);

        conselho.inativar();

        return repository.salvar(conselho);
    }
}
