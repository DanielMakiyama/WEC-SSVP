package com.daniel.wec_ssvp.service;

import com.daniel.wec_ssvp.dto.ConselhoParticularRequestDTO;
import com.daniel.wec_ssvp.dto.ConselhoParticularResponseDTO;
import com.daniel.wec_ssvp.entity.ConselhoParticular;
import com.daniel.wec_ssvp.repository.ConselhoParticularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConselhoParticularService {

    @Autowired
    private ConselhoParticularRepository repository;

    public ConselhoParticularResponseDTO salvar(ConselhoParticularRequestDTO dto) {
        ConselhoParticular conselho = new ConselhoParticular();
        conselho.setNome(dto.nome());
        conselho.setCidade(dto.cidade());
        conselho.setDataFundacao(dto.dataFundacao());
        conselho.setAtivo(true);

        ConselhoParticular salvo = repository.save(conselho);
        return toResponseDTO(salvo);
    }

    public List<ConselhoParticularResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponseDTO) //
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        ConselhoParticular conselho = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conselho não encontrado"));
        conselho.setAtivo(false); // Soft delete
        repository.save(conselho);
    }

    private ConselhoParticularResponseDTO toResponseDTO(ConselhoParticular conselho) {
        return new ConselhoParticularResponseDTO(
                conselho.getId(),
                conselho.getNome(),
                conselho.getCidade(),
                conselho.getDataFundacao(),
                conselho.getAtivo()
        );
    }
}