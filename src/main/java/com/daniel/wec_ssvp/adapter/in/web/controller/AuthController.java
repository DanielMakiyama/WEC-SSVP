package com.daniel.wec_ssvp.adapter.in.web.controller;

import com.daniel.wec_ssvp.adapter.in.web.dto.UsuarioRequestDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.login.LoginRequestDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.login.LoginResponseDTO;
import com.daniel.wec_ssvp.application.port.in.usuario.AutenticarUsuarioUseCase;
import com.daniel.wec_ssvp.application.port.in.usuario.CriarUsuarioUseCase;
import com.daniel.wec_ssvp.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Adaptador de entrada: login e cadastro de usuários via REST. */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthController(CriarUsuarioUseCase criarUsuarioUseCase,
                          AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = criarUsuarioUseCase.executar(
                new CriarUsuarioUseCase.Command(dto.email(), dto.senha(), dto.tipoUsuario())
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário criado com sucesso: " + usuario.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        String token = autenticarUsuarioUseCase.autenticar(dto.email(), dto.senha());

        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
    }
}
