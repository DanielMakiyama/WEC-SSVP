package com.daniel.wec_ssvp.application.usecase.assistido;

import com.daniel.wec_ssvp.application.port.in.assistido.ListarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.Assistido;

import java.util.List;
import java.util.UUID;

public class ListarAssistidoService implements ListarAssistidoUseCase {

    private final AssistidoRepositoryPort repository;

    public ListarAssistidoService(AssistidoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Assistido> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public Assistido buscarPorId(UUID id) {
        return repository.buscarPorId(id)
                .orElseThrow(AssistidoNaoEncontradoException::new);
    }
}
