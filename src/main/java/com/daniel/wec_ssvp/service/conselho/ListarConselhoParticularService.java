package com.daniel.wec_ssvp.service.conselho;

import com.daniel.wec_ssvp.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.entity.ConselhoParticular;
import com.daniel.wec_ssvp.repository.ConselhoParticularRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarConselhoParticularService {

    private final ConselhoParticularRepository repository;

    public ListarConselhoParticularService(
            ConselhoParticularRepository repository) {
        this.repository = repository;
    }

    public List<ConsultaResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ConsultaResponseDTO buscarPorId(UUID id) {
        ConselhoParticular conselho = repository.findById(id)
                .orElseThrow(ConselhoParticularNaoEncontradoException::new);

        return toResponseDTO(conselho);
    }

    private ConsultaResponseDTO toResponseDTO(ConselhoParticular conselhoParticular) {
        return new ConsultaResponseDTO(
                conselhoParticular.getId(),
                conselhoParticular.getNome(),
                conselhoParticular.getAtivo()
        );
    }
}
