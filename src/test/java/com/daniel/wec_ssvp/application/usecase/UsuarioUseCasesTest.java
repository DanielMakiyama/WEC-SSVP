package com.daniel.wec_ssvp.application.usecase;

import com.daniel.wec_ssvp.application.port.in.usuario.CriarUsuarioUseCase;
import com.daniel.wec_ssvp.application.usecase.usuario.AutenticarUsuarioService;
import com.daniel.wec_ssvp.application.usecase.usuario.CriarUsuarioService;
import com.daniel.wec_ssvp.domain.exception.CredenciaisInvalidasException;
import com.daniel.wec_ssvp.domain.exception.EmailJaCadastradoException;
import com.daniel.wec_ssvp.domain.model.TipoUsuario;
import com.daniel.wec_ssvp.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioUseCasesTest {

    private PortasEmMemoria.UsuariosEmMemoria usuarios;
    private CriarUsuarioService criar;
    private AutenticarUsuarioService autenticar;

    @BeforeEach
    void setUp() {
        usuarios = new PortasEmMemoria.UsuariosEmMemoria();
        PortasEmMemoria.CodificadorFalso codificador = new PortasEmMemoria.CodificadorFalso();
        criar = new CriarUsuarioService(usuarios, codificador);
        autenticar = new AutenticarUsuarioService(usuarios, codificador, new PortasEmMemoria.GeradorTokenFalso());
    }

    private Usuario criarVoluntario() {
        return criar.executar(new CriarUsuarioUseCase.Command("joao@ssvp.com", "senha123", TipoUsuario.VOLUNTARIO));
    }

    @Test
    void criaUsuarioComSenhaCriptografada() {
        Usuario salvo = criarVoluntario();

        assertNotNull(salvo.getId());
        assertTrue(salvo.isAtivo());
        assertEquals("hash:senha123", salvo.getSenhaHash());
        assertNotEquals("senha123", salvo.getSenhaHash());
    }

    @Test
    void naoPermiteEmailRepetido() {
        criarVoluntario();

        assertThrows(EmailJaCadastradoException.class, this::criarVoluntario);
    }

    @Test
    void loginCorretoDevolveTokenComOCargo() {
        criarVoluntario();

        String token = autenticar.autenticar("joao@ssvp.com", "senha123");

        assertEquals("token-joao@ssvp.com-VOLUNTARIO", token);
    }

    @Test
    void loginComSenhaErradaFalha() {
        criarVoluntario();

        assertThrows(CredenciaisInvalidasException.class,
                () -> autenticar.autenticar("joao@ssvp.com", "errada"));
    }

    @Test
    void loginComEmailInexistenteFalha() {
        assertThrows(CredenciaisInvalidasException.class,
                () -> autenticar.autenticar("ninguem@ssvp.com", "senha123"));
    }

    @Test
    void usuarioInativoNaoConsegueEntrar() {
        Usuario u = criarVoluntario();
        usuarios.salvar(new Usuario(u.getId(), u.getEmail(), u.getSenhaHash(), u.getTipoUsuario(), false));

        assertThrows(CredenciaisInvalidasException.class,
                () -> autenticar.autenticar("joao@ssvp.com", "senha123"));
    }
}
