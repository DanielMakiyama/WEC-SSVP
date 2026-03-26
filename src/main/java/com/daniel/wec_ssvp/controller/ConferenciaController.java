package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.dto.ConferenciaRequestDTO;
import com.daniel.wec_ssvp.dto.ConferenciaResponseDTO;
import com.daniel.wec_ssvp.service.ConferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conferencias")
public class ConferenciaController {

    @Autowired
    private ConferenciaService service;

    @PostMapping("/criar")
    public ResponseEntity<ConferenciaResponseDTO> create(@RequestBody ConferenciaRequestDTO dto) {
        ConferenciaResponseDTO salva = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ConferenciaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.listarTodas());
    }
    
    @GetMapping("/buscarConselho/{conselhoId}")
    public ResponseEntity<List<ConferenciaResponseDTO>> findByConselho(@PathVariable UUID conselhoId) {
        return ResponseEntity.ok(service.listarPorConselho(conselhoId));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}