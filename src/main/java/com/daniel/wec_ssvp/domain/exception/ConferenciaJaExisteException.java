package com.daniel.wec_ssvp.domain.exception;

public class ConferenciaJaExisteException extends RecursoJaExisteException {

    public ConferenciaJaExisteException() {
        super("Já existe uma conferência com esse nome para este conselho");
    }
}
