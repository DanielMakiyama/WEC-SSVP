package com.daniel.wec_ssvp.application.usecase.conselho;

import com.daniel.wec_ssvp.application.port.in.conselho.CriarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConselhoJaExisteException;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

public class CriarConselhoParticularService implements CriarConselhoParticularUseCase {

    private final ConselhoParticularRepositoryPort repository;

    public CriarConselhoParticularService(ConselhoParticularRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ConselhoParticular executar(Command command) {
        repository.buscarPorNome(command.nome())
                .ifPresent(existente -> {
                    throw new ConselhoJaExisteException();
                });

        ConselhoParticular conselho = ConselhoParticular.novo(
                command.nome(),
                command.cidade(),
                command.dataFundacao()
        );

        return repository.salvar(conselho);
    }
}
