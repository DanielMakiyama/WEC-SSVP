package com.daniel.wec_ssvp.domain.exception;

/** RN012: o e-mail deve ser único no sistema. */
public class EmailJaCadastradoException extends RecursoJaExisteException {

    public EmailJaCadastradoException() {
        super("Email já cadastrado");
    }
}
