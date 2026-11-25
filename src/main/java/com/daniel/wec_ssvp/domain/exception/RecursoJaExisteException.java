package com.daniel.wec_ssvp.domain.exception;

/** Já existe um registro com os mesmos dados únicos (HTTP 409). */
public abstract class RecursoJaExisteException extends DomainException {

    protected RecursoJaExisteException(String message) {
        super(message);
    }
}
