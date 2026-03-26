package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.dto.ConselhoParticularRequestDTO;
import com.daniel.wec_ssvp.dto.ConselhoParticularResponseDTO;
import com.daniel.wec_ssvp.service.ConselhoParticularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conselhos")
public class ConselhoParticularController {

    @Autowired
    private ConselhoParticularService service;

    @PostMapping("/criar")
    public ResponseEntity<ConselhoParticularResponseDTO> create(@RequestBody ConselhoParticularRequestDTO dto) {
        ConselhoParticularResponseDTO salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ConselhoParticularResponseDTO>> findAll() {
        List<ConselhoParticularResponseDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}