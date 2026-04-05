package com.daniel.wec_ssvp.exception;

public class ConferenciaNaoEncontradaException extends RuntimeException {
    public ConferenciaNaoEncontradaException () {
        super("Conferência não encontrada");
    }
}

