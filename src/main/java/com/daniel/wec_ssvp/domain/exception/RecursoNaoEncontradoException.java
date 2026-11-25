package com.daniel.wec_ssvp.domain.exception;

/** O registro procurado não existe (HTTP 404). */
public abstract class RecursoNaoEncontradoException extends DomainException {

    protected RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
