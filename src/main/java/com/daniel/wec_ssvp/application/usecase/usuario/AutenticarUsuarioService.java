package com.daniel.wec_ssvp.application.usecase.usuario;

import com.daniel.wec_ssvp.application.port.in.usuario.AutenticarUsuarioUseCase;
import com.daniel.wec_ssvp.application.port.out.CodificadorSenhaPort;
import com.daniel.wec_ssvp.application.port.out.GeradorTokenPort;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.domain.exception.CredenciaisInvalidasException;
import com.daniel.wec_ssvp.domain.model.Usuario;

public class AutenticarUsuarioService implements AutenticarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final CodificadorSenhaPort codificadorSenha;
    private final GeradorTokenPort geradorToken;

    public AutenticarUsuarioService(UsuarioRepositoryPort usuarioRepository,
                                    CodificadorSenhaPort codificadorSenha,
                                    GeradorTokenPort geradorToken) {
        this.usuarioRepository = usuarioRepository;
        this.codificadorSenha = codificadorSenha;
        this.geradorToken = geradorToken;
    }

    @Override
    public String autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .filter(Usuario::isAtivo)
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!codificadorSenha.confere(senha, usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException();
        }

        return geradorToken.gerar(usuario);
    }
}
