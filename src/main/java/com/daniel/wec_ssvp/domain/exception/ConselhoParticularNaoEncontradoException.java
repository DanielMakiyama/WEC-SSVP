package com.daniel.wec_ssvp.domain.exception;

public class ConselhoParticularNaoEncontradoException extends RecursoNaoEncontradoException {

    public ConselhoParticularNaoEncontradoException() {
        super("Conselho Particular não encontrado");
    }
}
