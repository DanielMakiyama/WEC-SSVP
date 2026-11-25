package com.daniel.wec_ssvp.application.usecase.usuario;

import com.daniel.wec_ssvp.application.port.in.usuario.CriarUsuarioUseCase;
import com.daniel.wec_ssvp.application.port.out.CodificadorSenhaPort;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.EmailJaCadastradoException;
import com.daniel.wec_ssvp.domain.model.Usuario;

public class CriarUsuarioService implements CriarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final CodificadorSenhaPort codificadorSenha;

    public CriarUsuarioService(UsuarioRepositoryPort usuarioRepository,
                               CodificadorSenhaPort codificadorSenha) {
        this.usuarioRepository = usuarioRepository;
        this.codificadorSenha = codificadorSenha;
    }

    @Override
    public Usuario executar(Command command) {
        usuarioRepository.buscarPorEmail(command.email())
                .ifPresent(existente -> {
                    throw new EmailJaCadastradoException();
                });

        Usuario usuario = Usuario.novo(
                command.email(),
                codificadorSenha.codificar(command.senha()),
                command.tipoUsuario()
        );

        return usuarioRepository.salvar(usuario);
    }
}
