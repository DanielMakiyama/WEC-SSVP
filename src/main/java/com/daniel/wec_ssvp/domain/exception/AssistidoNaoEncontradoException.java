package com.daniel.wec_ssvp.domain.exception;

public class AssistidoNaoEncontradoException extends RecursoNaoEncontradoException {

    public AssistidoNaoEncontradoException() {
        super("Assistido não encontrado");
    }
}
