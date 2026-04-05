package com.daniel.wec_ssvp.exception;

public class ConselhoParticularNaoEncontradoException extends RuntimeException {
    public ConselhoParticularNaoEncontradoException() {
        super("Conselho Particular não encontrado");
    }
}
