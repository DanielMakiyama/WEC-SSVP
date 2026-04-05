package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.model.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.model.dto.cadastro.ConselhoParticularRequestDTO;
import com.daniel.wec_ssvp.service.conselho.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conselhos")
public class ConselhoParticularController {

    private static final Logger log =
            LoggerFactory.getLogger(ConselhoParticularController.class);

    private final CriarConselhoParticularService criarService;
    private final ListarConselhoParticularService listarService;
    private final DeletarConselhoParticularService deletarService;

    public ConselhoParticularController(
            CriarConselhoParticularService criarService,
            ListarConselhoParticularService listarService,
            DeletarConselhoParticularService deletarService) {

        this.criarService = criarService;
        this.listarService = listarService;
        this.deletarService = deletarService;
    }


    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/criar")
    public ResponseEntity<CriacaoReuniaoResponseDTO> create(
            @Valid @RequestBody ConselhoParticularRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] POST {} - nome={}", path, dto.nome());

        var salvo = criarService.executar(dto);

        log.info("[OUT] POST {} - status=201 id={}", path, salvo.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }


    @GetMapping("/buscar")
    public ResponseEntity<?> find(
            @RequestParam(required = false) UUID id,
            HttpServletRequest request) {

        String path = request.getRequestURI();

        if (id != null) {
            log.info("[IN ] GET {} - id={}", path, id);

            var response = listarService.buscarPorId(id);

            log.info("[OUT] GET {} - status=200 id={}", path, response.id());

            return ResponseEntity.ok(response);
        }

        log.info("[IN ] GET {}", path);

        var lista = listarService.listarTodos();

        log.info("[OUT] GET {} - status=200 total={}", path, lista.size());

        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<DeleteResponseDTO> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] DELETE {} - id={}", path, id);

        var response = deletarService.deleteById(id);

        log.info("[OUT] DELETE {} - status=204 id={}", path, id);

        return ResponseEntity.ok(response);
    }
}