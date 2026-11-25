package com.daniel.wec_ssvp.domain.exception;

public class ConselhoJaExisteException extends RecursoJaExisteException {

    public ConselhoJaExisteException() {
        super("Já existe um Conselho com esse nome");
    }
}
