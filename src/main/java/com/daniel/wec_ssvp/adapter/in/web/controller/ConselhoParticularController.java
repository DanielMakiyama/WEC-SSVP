package com.daniel.wec_ssvp.adapter.in.web.controller;

import com.daniel.wec_ssvp.adapter.in.web.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.cadastro.ConselhoParticularRequestDTO;
import com.daniel.wec_ssvp.adapter.in.web.mapper.WebMapper;
import com.daniel.wec_ssvp.application.port.in.conselho.CriarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.in.conselho.DeletarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.in.conselho.ListarConselhoParticularUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/** Adaptador de entrada: expõe os casos de uso de Conselhos Particulares via REST. */
@RestController
@RequestMapping("/conselhos")
public class ConselhoParticularController {

    private static final Logger log =
            LoggerFactory.getLogger(ConselhoParticularController.class);

    private final CriarConselhoParticularUseCase criarUseCase;
    private final ListarConselhoParticularUseCase listarUseCase;
    private final DeletarConselhoParticularUseCase deletarUseCase;

    public ConselhoParticularController(
            CriarConselhoParticularUseCase criarUseCase,
            ListarConselhoParticularUseCase listarUseCase,
            DeletarConselhoParticularUseCase deletarUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.deletarUseCase = deletarUseCase;
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/criar")
    public ResponseEntity<CriacaoReuniaoResponseDTO> create(
            @Valid @RequestBody ConselhoParticularRequestDTO dto,
            HttpServletRequest request) {

        String path = request.getRequestURI();
        log.info("[IN ] POST {} - nome={}", path, dto.nome());

        var salvo = WebMapper.toCriacaoResponse(
                criarUseCase.executar(new CriarConselhoParticularUseCase.Command(
                        dto.nome(), dto.cidade(), dto.dataFundacao()))
        );

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
            var response = WebMapper.toConsulta(listarUseCase.buscarPorId(id));
            log.info("[OUT] GET {} - status=200 id={}", path, response.id());
            return ResponseEntity.ok(response);
        }

        log.info("[IN ] GET {}", path);
        var lista = listarUseCase.listarTodos().stream()
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
