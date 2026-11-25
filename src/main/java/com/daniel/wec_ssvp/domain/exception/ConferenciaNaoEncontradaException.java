package com.daniel.wec_ssvp.domain.exception;

public class ConferenciaNaoEncontradaException extends RecursoNaoEncontradoException {

    public ConferenciaNaoEncontradaException() {
        super("Conferência não encontrada");
    }
}
