package com.daniel.wec_ssvp.application.usecase.assistido;

import com.daniel.wec_ssvp.application.port.in.assistido.AssistidoCommand;
import com.daniel.wec_ssvp.application.port.in.assistido.AtualizarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.domain.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;

import java.util.UUID;

public class AtualizarAssistidoService implements AtualizarAssistidoUseCase {

    private final AssistidoRepositoryPort assistidoRepository;
    private final ConferenciaRepositoryPort conferenciaRepository;

    public AtualizarAssistidoService(
            AssistidoRepositoryPort assistidoRepository,
            ConferenciaRepositoryPort conferenciaRepository) {
        this.assistidoRepository = assistidoRepository;
        this.conferenciaRepository = conferenciaRepository;
    }

    @Override
    public Assistido executar(UUID id, AssistidoCommand command) {
        Assistido assistido = assistidoRepository.buscarPorId(id)
                .orElseThrow(AssistidoNaoEncontradoException::new);

        Conferencia conferencia = conferenciaRepository.buscarPorId(command.conferenciaId())
                .orElseThrow(ConferenciaNaoEncontradaException::new);

        assistido.atualizar(command.dados(), conferencia);

        return assistidoRepository.salvar(assistido);
    }
}
