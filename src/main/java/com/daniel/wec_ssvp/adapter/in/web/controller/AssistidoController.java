package com.daniel.wec_ssvp.adapter.in.web.controller;

import com.daniel.wec_ssvp.adapter.in.web.dto.CriacaoUsuarioResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.cadastro.AssistidoRequestDTO;
import com.daniel.wec_ssvp.adapter.in.web.mapper.WebMapper;
import com.daniel.wec_ssvp.application.port.in.assistido.AtualizarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.assistido.CriarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.assistido.DeletarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.assistido.ListarAssistidoUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/** Adaptador de entrada: expõe os casos de uso de assistidos via REST. */
@RestController
@RequestMapping("/assistidos")
public class AssistidoController {

    private static final Logger log =
            LoggerFactory.getLogger(AssistidoController.class);

    private final CriarAssistidoUseCase criarUseCase;
    private final ListarAssistidoUseCase listarUseCase;
    private final AtualizarAssistidoUseCase atualizarUseCase;
    private final DeletarAssistidoUseCase deletarUseCase;

    public AssistidoController(
            CriarAssistidoUseCase criarUseCase,
            ListarAssistidoUseCase listarUseCase,
            AtualizarAssistidoUseCase atualizarUseCase,
            DeletarAssistidoUseCase deletarUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/criar")
    public ResponseEntity<CriacaoUsuarioResponseDTO> create(
            @Valid @RequestBody AssistidoRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] POST {} - nome={}", path, dto.dadosPessoais().nome());

        var response = WebMapper.toCriacaoResponse(
                criarUseCase.executar(WebMapper.toCommand(dto))
        );

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
            var response = WebMapper.toDetalhe(listarUseCase.buscarPorId(id));
            log.info("[OUT] GET {} - status=200 id={}", path, response.id());
            return ResponseEntity.ok(response);
        }

        log.info("[IN ] GET {}", path);
        var lista = listarUseCase.listarTodos().stream()
                .map(WebMapper::toResumo)
                .toList();
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

        var response = WebMapper.toCriacaoResponse(
                atualizarUseCase.executar(id, WebMapper.toCommand(dto))
        );

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

        var response = WebMapper.toDeleteResponse(deletarUseCase.executar(id));

        log.info("[OUT] DELETE {} - status=204 id={}", path, id);
        return ResponseEntity.ok(response);
    }
}
