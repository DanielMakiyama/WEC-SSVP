package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.model.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.model.dto.cadastro.ConferenciaRequestDTO;
import com.daniel.wec_ssvp.service.conferencia.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@RestController
@RequestMapping("/conferencias")
public class ConferenciaController {

    private static final Logger log =
            LoggerFactory.getLogger(ConferenciaController.class);

    private final CriarConferenciaService criarService;
    private final ListarConferenciaService listarService;
    private final DeletarConferenciaService deletarService;

    public ConferenciaController(
            CriarConferenciaService criarService,
            ListarConferenciaService listarService,
            DeletarConferenciaService deletarService) {

        this.criarService = criarService;
        this.listarService = listarService;
        this.deletarService = deletarService;
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/criar")
    public ResponseEntity<CriacaoReuniaoResponseDTO> create(
            @Valid @RequestBody ConferenciaRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] POST {} - nome={}", path, dto.nome());

        var salvo = criarService.salvar(dto);

        log.info("[OUT] POST {} - status=201 id={}", path, salvo.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }



    //como usar:
    //buscar geral: GET /conferencias/buscar
    //buscar por idConferencia?: GET/conferencias/buscar?id=uuid
    //buscar por idConselho?: GET/conferencias/buscar?conselhoId=uuid
    @GetMapping("/buscar")
    public ResponseEntity<?> find(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) UUID conselhoId,
            HttpServletRequest request) {

        String path = request.getRequestURI();

        if (id != null) {
            log.info("[IN ] GET {} - id={}", path, id);

            var response = listarService.buscarPorId(id);

            log.info("[OUT] GET {} - status=200 id={}", path, response.id());

            return ResponseEntity.ok(response);
        }

        if (conselhoId != null) {
            log.info("[IN ] GET {} - conselhoId={}", path, conselhoId);

            var lista = listarService.listarPorConselho(conselhoId);

            log.info("[OUT] GET {} - status=200 total={}", path, lista.size());

            return ResponseEntity.ok(lista);
        }

        log.info("[IN ] GET {}", path);

        var lista = listarService.listarTodas();

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