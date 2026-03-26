package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.dto.ConferenciaRequestDTO;
import com.daniel.wec_ssvp.dto.ConferenciaResponseDTO;
import com.daniel.wec_ssvp.service.ConferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conferencias")
public class ConferenciaController {

    @Autowired
    private ConferenciaService service;

    @PostMapping
    public ResponseEntity<ConferenciaResponseDTO> criar(@RequestBody ConferenciaRequestDTO dto) {
        ConferenciaResponseDTO salva = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<ConferenciaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }
    
    @GetMapping("/conselho/{conselhoId}")
    public ResponseEntity<List<ConferenciaResponseDTO>> listarPorConselho(@PathVariable Integer conselhoId) {
        return ResponseEntity.ok(service.listarPorConselho(conselhoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Integer id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}