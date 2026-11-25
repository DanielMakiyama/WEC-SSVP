package com.daniel.wec_ssvp.application.port.in.conselho;

import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

import java.time.LocalDate;

/** Porta de entrada: cadastrar um Conselho Particular. */
public interface CriarConselhoParticularUseCase {

    ConselhoParticular executar(Command command);

    record Command(String nome, String cidade, LocalDate dataFundacao) {}
}
