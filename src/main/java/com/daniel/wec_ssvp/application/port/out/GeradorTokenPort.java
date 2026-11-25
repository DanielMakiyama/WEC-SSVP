package com.daniel.wec_ssvp.application.port.out;

import com.daniel.wec_ssvp.domain.model.Usuario;

/** Porta de saída: emissão do token de acesso. Implementada com JWT. */
public interface GeradorTokenPort {

    String gerar(Usuario usuario);
}
