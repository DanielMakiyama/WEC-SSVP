package com.daniel.wec_ssvp.exception;

public class ConferenciaJaExisteException extends RuntimeException {

    public ConferenciaJaExisteException() {
        super("Já existe uma conferência com esse nome para este conselho");
    }
}
