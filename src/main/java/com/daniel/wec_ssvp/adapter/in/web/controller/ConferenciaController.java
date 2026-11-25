package com.daniel.wec_ssvp.adapter.in.web.controller;

import com.daniel.wec_ssvp.adapter.in.web.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.cadastro.ConferenciaRequestDTO;
import com.daniel.wec_ssvp.adapter.in.web.mapper.WebMapper;
import com.daniel.wec_ssvp.application.port.in.conferencia.CriarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.in.conferencia.DeletarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.in.conferencia.ListarConferenciaUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/** Adaptador de entrada: expõe os casos de uso de Conferências via REST. */
@RestController
@RequestMapping("/conferencias")
public class ConferenciaController {

    private static final Logger log =
            LoggerFactory.getLogger(ConferenciaController.class);

    private final CriarConferenciaUseCase criarUseCase;
    private final ListarConferenciaUseCase listarUseCase;
    private final DeletarConferenciaUseCase deletarUseCase;

    public ConferenciaController(
            CriarConferenciaUseCase criarUseCase,
            ListarConferenciaUseCase listarUseCase,
            DeletarConferenciaUseCase deletarUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.deletarUseCase = deletarUseCase;
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/criar")
    public ResponseEntity<CriacaoReuniaoResponseDTO> create(
            @Valid @RequestBody ConferenciaRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] POST {} - nome={}", path, dto.nome());

        var salvo = WebMapper.toCriacaoResponse(
                criarUseCase.executar(new CriarConferenciaUseCase.Command(dto.nome(), dto.conselhoParticularId()))
        );

        log.info("[OUT] POST {} - status=201 id={}", path, salvo.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // como usar:
    // buscar geral:            GET /conferencias/buscar
    // buscar por idConferencia: GET /conferencias/buscar?id=uuid
    // buscar por idConselho:    GET /conferencias/buscar?conselhoId=uuid
    @GetMapping("/buscar")
    public ResponseEntity<?> find(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) UUID conselhoId,
            HttpServletRequest request) {

        String path = request.getRequestURI();

        if (id != null) {
            log.info("[IN ] GET {} - id={}", path, id);
            var response = WebMapper.toConsulta(listarUseCase.buscarPorId(id));
            log.info("[OUT] GET {} - status=200 id={}", path, response.id());
            return ResponseEntity.ok(response);
        }

        if (conselhoId != null) {
            log.info("[IN ] GET {} - conselhoId={}", path, conselhoId);
            var lista = listarUseCase.listarPorConselho(conselhoId).stream()
                    .map(WebMapper::toConsulta)
                    .toList();
            log.info("[OUT] GET {} - status=200 total={}", path, lista.size());
            return ResponseEntity.ok(lista);
        }

        log.info("[IN ] GET {}", path);
        var lista = listarUseCase.listarTodas().stream()
                .map(WebMapper::toConsulta)
                .toList();
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

        var response = WebMapper.toDeleteResponse(deletarUseCase.executar(id));

        log.info("[OUT] DELETE {} - status=204 id={}", path, id);
        return ResponseEntity.ok(response);
    }
}
