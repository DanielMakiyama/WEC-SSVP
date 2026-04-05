package com.daniel.wec_ssvp.exception;

public class ConselhoJaExisteException extends RuntimeException {

    public ConselhoJaExisteException() {
        super("Já existe um Conselho com esse nome");
    }
}