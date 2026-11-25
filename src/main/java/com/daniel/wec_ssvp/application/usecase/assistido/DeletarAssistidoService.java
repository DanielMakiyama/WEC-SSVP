package com.daniel.wec_ssvp.application.usecase.assistido;

import com.daniel.wec_ssvp.application.port.in.assistido.DeletarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.Assistido;

import java.util.UUID;

public class DeletarAssistidoService implements DeletarAssistidoUseCase {

    private final AssistidoRepositoryPort repository;

    public DeletarAssistidoService(AssistidoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Assistido executar(UUID id) {
        Assistido assistido = repository.buscarPorId(id)
                .orElseThrow(AssistidoNaoEncontradoException::new);

        assistido.inativar();

        return repository.salvar(assistido);
    }
}
