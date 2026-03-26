package com.daniel.wec_ssvp.service;

import com.daniel.wec_ssvp.dto.ConferenciaRequestDTO;
import com.daniel.wec_ssvp.dto.ConferenciaResponseDTO;
import com.daniel.wec_ssvp.model.Conferencia;
import com.daniel.wec_ssvp.model.ConselhoParticular;
import com.daniel.wec_ssvp.repository.ConferenciaRepository;
import com.daniel.wec_ssvp.repository.ConselhoParticularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenciaService {

    @Autowired
    private ConferenciaRepository conferenciaRepository;

    @Autowired
    private ConselhoParticularRepository conselhoRepository;

    public ConferenciaResponseDTO salvar(ConferenciaRequestDTO dto) {

        // Busca o Conselho no banco para garantir que existe
        ConselhoParticular conselho = conselhoRepository.findById(dto.conselhoParticularId())
                .orElseThrow(() -> new RuntimeException("Conselho Particular não encontrado."));

        // Monta a Conferência
        Conferencia conferencia = new Conferencia();
        conferencia.setNome(dto.nome());
        conferencia.setAtivo(true);
        conferencia.setConselhoParticular(conselho); // Faz a amarração!

        // Salva e converte
        Conferencia salva = conferenciaRepository.save(conferencia);
        return toResponseDTO(salva);
    }

    public List<ConferenciaResponseDTO> listarTodas() {
        return conferenciaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ConferenciaResponseDTO> listarPorConselho(Integer conselhoId) {
        return conferenciaRepository.findByConselhoParticularId(conselhoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void desativar(Integer id) {
        Conferencia conferencia = conferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conferência não encontrada"));
        conferencia.setAtivo(false); // Soft Delete
        conferenciaRepository.save(conferencia);
    }


    private ConferenciaResponseDTO toResponseDTO(Conferencia conferencia) {
        return new ConferenciaResponseDTO(
                conferencia.getId(),
                conferencia.getNome(),
                conferencia.getAtivo(),
                conferencia.getConselhoParticular().getId(),
                conferencia.getConselhoParticular().getNome()
        );
    }
}