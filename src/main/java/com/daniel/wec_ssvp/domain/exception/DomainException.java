package com.daniel.wec_ssvp.domain.exception;

/**
 * Base de todas as exceções de regra de negócio.
 * O adaptador web traduz cada subtipo para o status HTTP adequado.
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}
