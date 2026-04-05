package com.daniel.wec_ssvp.service.conselho;

import com.daniel.wec_ssvp.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.model.entity.ConselhoParticular;
import com.daniel.wec_ssvp.repository.ConselhoParticularRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarConselhoParticularService {

    private final ConselhoParticularRepository repository;

    public DeletarConselhoParticularService(
            ConselhoParticularRepository repository) {
        this.repository = repository;
    }

    public DeleteResponseDTO deleteById(UUID id) {

        ConselhoParticular conselho = repository.findById(id)
                .orElseThrow(ConselhoParticularNaoEncontradoException::new);

        conselho.setAtivo(false);

        ConselhoParticular salvo = repository.save(conselho);

        return toResponseDTO(salvo);
    }

    private DeleteResponseDTO toResponseDTO(ConselhoParticular conselho) {
        return DeleteResponseDTO.criado(
                conselho.getId(),
                conselho.getNome(),
                conselho.getAtivo(),
                conselho.getMessage()
        );
    }
}

