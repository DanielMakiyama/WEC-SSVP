package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.dto.ConselhoParticularRequestDTO;
import com.daniel.wec_ssvp.dto.ConselhoParticularResponseDTO;
import com.daniel.wec_ssvp.service.ConselhoParticularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conselhos")
public class ConselhoParticularController {

    @Autowired
    private ConselhoParticularService service;

    @PostMapping
    public ResponseEntity<ConselhoParticularResponseDTO> criar(@RequestBody ConselhoParticularRequestDTO dto) {
        ConselhoParticularResponseDTO salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<ConselhoParticularResponseDTO>> listar() {
        List<ConselhoParticularResponseDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Integer id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}