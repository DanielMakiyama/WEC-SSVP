package com.daniel.wec_ssvp.application.usecase.assistido;

import com.daniel.wec_ssvp.application.port.in.assistido.AssistidoCommand;
import com.daniel.wec_ssvp.application.port.in.assistido.CriarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;

public class CriarAssistidoService implements CriarAssistidoUseCase {

    private final AssistidoRepositoryPort assistidoRepository;
    private final ConferenciaRepositoryPort conferenciaRepository;

    public CriarAssistidoService(
            AssistidoRepositoryPort assistidoRepository,
            ConferenciaRepositoryPort conferenciaRepository) {
        this.assistidoRepository = assistidoRepository;
        this.conferenciaRepository = conferenciaRepository;
    }

    @Override
    public Assistido executar(AssistidoCommand command) {
        Conferencia conferencia = conferenciaRepository.buscarPorId(command.conferenciaId())
                .orElseThrow(ConferenciaNaoEncontradaException::new);

        return assistidoRepository.salvar(Assistido.novo(command.dados(), conferencia));
    }
}
