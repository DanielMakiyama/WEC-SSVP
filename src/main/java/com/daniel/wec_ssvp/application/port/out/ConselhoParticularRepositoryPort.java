package com.daniel.wec_ssvp.application.port.out;

import com.daniel.wec_ssvp.domain.model.ConselhoParticular;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Porta de saída: persistência de Conselhos Particulares. */
public interface ConselhoParticularRepositoryPort {

    ConselhoParticular salvar(ConselhoParticular conselho);

    Optional<ConselhoParticular> buscarPorId(UUID id);

    Optional<ConselhoParticular> buscarPorNome(String nome);

    List<ConselhoParticular> listarTodos();
}
