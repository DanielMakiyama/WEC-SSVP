package com.daniel.wec_ssvp.exception;

public class AssistidoNaoEncontradoException extends RuntimeException {
    public AssistidoNaoEncontradoException () {
        super("Assistido não encontrado");
    }
}
