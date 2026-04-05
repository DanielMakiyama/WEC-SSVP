package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.model.dto.CriacaoUsuarioResponseDTO;
import com.daniel.wec_ssvp.model.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.model.dto.cadastro.AssistidoRequestDTO;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.service.assistido.*;

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
@RequestMapping("/assistidos")
public class AssistidoController {

    private static final Logger log =
            LoggerFactory.getLogger(AssistidoController.class);

    private final CriarAssistidoService criarService;
    private final ListarAssistidoService listarService;
    private final AtualizarAssistidoService atualizarService;
    private final DeletarAssistidoService deletarService;

    public AssistidoController(
            CriarAssistidoService criarService,
            ListarAssistidoService listarService,
            AtualizarAssistidoService atualizarService,
            DeletarAssistidoService deletarService) {

        this.criarService = criarService;
        this.listarService = listarService;
        this.atualizarService = atualizarService;
        this.deletarService = deletarService;
    }


    @PostMapping("/criar")
    public ResponseEntity<CriacaoUsuarioResponseDTO> create(
            @Valid @RequestBody AssistidoRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] POST {} - nome={}", path, dto.dadosPessoais().nome());

        var response = criarService.executar(dto);

        log.info("[OUT] POST {} - status=201 id={}", path, response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CriacaoUsuarioResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody AssistidoRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] PUT {} - id={}", path, id);

        var response = atualizarService.executar(id, dto);

        log.info("[OUT] PUT {} - status=200 id={}", path, id);

        return ResponseEntity.ok(response);
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