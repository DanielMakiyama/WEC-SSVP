package com.daniel.wec_ssvp.application.usecase.conferencia;

import com.daniel.wec_ssvp.application.port.in.conferencia.CriarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConferenciaJaExisteException;
import com.daniel.wec_ssvp.domain.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

public class CriarConferenciaService implements CriarConferenciaUseCase {

    private final ConferenciaRepositoryPort conferenciaRepository;
    private final ConselhoParticularRepositoryPort conselhoRepository;

    public CriarConferenciaService(
            ConferenciaRepositoryPort conferenciaRepository,
            ConselhoParticularRepositoryPort conselhoRepository) {
        this.conferenciaRepository = conferenciaRepository;
        this.conselhoRepository = conselhoRepository;
    }

    @Override
    public Conferencia executar(Command command) {
        ConselhoParticular conselho = conselhoRepository
                .buscarPorId(command.conselhoParticularId())
                .orElseThrow(ConselhoParticularNaoEncontradoException::new);

        conferenciaRepository
                .buscarPorNomeEConselho(command.nome(), command.conselhoParticularId())
                .ifPresent(existente -> {
                    throw new ConferenciaJaExisteException();
                });

        return conferenciaRepository.salvar(Conferencia.nova(command.nome(), conselho));
    }
}
