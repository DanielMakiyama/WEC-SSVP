package com.daniel.wec_ssvp.service.conferencia;

import com.daniel.wec_ssvp.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.entity.Conferencia;
import com.daniel.wec_ssvp.repository.ConferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarConferenciaService {

    private final ConferenciaRepository conferenciaRepository;

    public ListarConferenciaService(ConferenciaRepository conferenciaRepository) {
        this.conferenciaRepository = conferenciaRepository;
    }

    public List<ConsultaResponseDTO> listarTodas() {
        return conferenciaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ConsultaResponseDTO buscarPorId(UUID id) {
        Conferencia conferencia = conferenciaRepository.findById(id)
                .orElseThrow(ConferenciaNaoEncontradaException::new);

        return toResponseDTO(conferencia);
    }

    public List<ConsultaResponseDTO> listarPorConselho(UUID conselhoId) {
        return conferenciaRepository.findByConselhoParticularId(conselhoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private ConsultaResponseDTO toResponseDTO(Conferencia conferencia) {
        return new ConsultaResponseDTO(
                conferencia.getId(),
                conferencia.getNome(),
                conferencia.getAtivo()
        );
    }
}