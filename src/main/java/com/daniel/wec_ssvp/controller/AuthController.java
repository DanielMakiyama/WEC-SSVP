package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.model.dto.login.LoginRequestDTO;
import com.daniel.wec_ssvp.model.dto.login.LoginResponseDTO;
import com.daniel.wec_ssvp.model.dto.UsuarioRequestDTO;
import com.daniel.wec_ssvp.model.entity.Usuario;
import com.daniel.wec_ssvp.repository.UsuarioRepository;
import com.daniel.wec_ssvp.security.JwtService;
import com.daniel.wec_ssvp.service.CriarUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CriarUsuarioService criarUsuarioService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthController(CriarUsuarioService criarUsuarioService,
                          AuthenticationManager authenticationManager,
                          UsuarioRepository usuarioRepository,
                          JwtService jwtService) {
        this.criarUsuarioService = criarUsuarioService;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = criarUsuarioService.executar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário criado com sucesso: " + usuario.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.gerarToken(
                usuario.getEmail(),
                usuario.getTipoUsuario().name()
        );

        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
    }
}