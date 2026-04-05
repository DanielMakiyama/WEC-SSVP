package com.daniel.wec_ssvp.service.conferencia;

import com.daniel.wec_ssvp.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.model.entity.Conferencia;
import com.daniel.wec_ssvp.model.entity.ConselhoParticular;
import com.daniel.wec_ssvp.repository.ConferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarConferenciaService {

    private final ConferenciaRepository conferenciaRepository;

    public DeletarConferenciaService(ConferenciaRepository conferenciaRepository) {
        this.conferenciaRepository = conferenciaRepository;
    }

    public DeleteResponseDTO deleteById(UUID id) {

        Conferencia conferencia = conferenciaRepository.findById(id)
                .orElseThrow(ConferenciaNaoEncontradaException::new);

        conferencia.setAtivo(false);

        Conferencia salvo = conferenciaRepository.save(conferencia);

        return toResponseDTO(salvo);
    }

    private DeleteResponseDTO toResponseDTO(Conferencia conferencia) {
        return DeleteResponseDTO.criado(
                conferencia.getId(),
                conferencia.getNome(),
                conferencia.getAtivo(),
                conferencia.getMessage()
        );
    }
}