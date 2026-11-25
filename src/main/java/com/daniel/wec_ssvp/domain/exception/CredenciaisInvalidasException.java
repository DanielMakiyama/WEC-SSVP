package com.daniel.wec_ssvp.domain.exception;

/**
 * Login com e-mail inexistente, senha errada ou usuário inativo (RN001, RN002).
 * A mensagem é a mesma nos três casos para não revelar quais e-mails existem.
 */
public class CredenciaisInvalidasException extends DomainException {

    public CredenciaisInvalidasException() {
        super("Email ou senha inválidos");
    }
}
