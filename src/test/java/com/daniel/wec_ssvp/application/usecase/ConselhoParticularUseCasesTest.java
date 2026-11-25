package com.daniel.wec_ssvp.application.usecase;

import com.daniel.wec_ssvp.application.port.in.conselho.CriarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.usecase.conselho.CriarConselhoParticularService;
import com.daniel.wec_ssvp.application.usecase.conselho.DeletarConselhoParticularService;
import com.daniel.wec_ssvp.application.usecase.conselho.ListarConselhoParticularService;
import com.daniel.wec_ssvp.domain.exception.ConselhoJaExisteException;
import com.daniel.wec_ssvp.domain.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConselhoParticularUseCasesTest {

    private PortasEmMemoria.ConselhosEmMemoria conselhos;
    private CriarConselhoParticularService criar;
    private ListarConselhoParticularService listar;
    private DeletarConselhoParticularService deletar;

    @BeforeEach
    void setUp() {
        conselhos = new PortasEmMemoria.ConselhosEmMemoria();
        criar = new CriarConselhoParticularService(conselhos);
        listar = new ListarConselhoParticularService(conselhos);
        deletar = new DeletarConselhoParticularService(conselhos);
    }

    private CriarConselhoParticularUseCase.Command comando(String nome) {
        return new CriarConselhoParticularUseCase.Command(nome, "Mogi das Cruzes", LocalDate.of(1950, 1, 1));
    }

    @Test
    void criaConselhoAtivoComId() {
        ConselhoParticular salvo = criar.executar(comando("CP Mogi"));

        assertNotNull(salvo.getId());
        assertEquals("CP Mogi", salvo.getNome());
        assertTrue(salvo.isAtivo());
    }

    @Test
    void naoPermiteDoisConselhosComMesmoNome() {
        criar.executar(comando("CP Mogi"));

        assertThrows(ConselhoJaExisteException.class, () -> criar.executar(comando("CP Mogi")));
        assertEquals(1, listar.listarTodos().size());
    }

    @Test
    void deletarApenasInativaOConselho() {
        UUID id = criar.executar(comando("CP Mogi")).getId();

        ConselhoParticular resultado = deletar.executar(id);

        assertFalse(resultado.isAtivo());
        assertFalse(listar.buscarPorId(id).isAtivo());
        assertEquals(1, listar.listarTodos().size());
    }

    @Test
    void buscarOuDeletarConselhoInexistenteLancaExcecao() {
        UUID inexistente = UUID.randomUUID();

        assertThrows(ConselhoParticularNaoEncontradoException.class, () -> listar.buscarPorId(inexistente));
        assertThrows(ConselhoParticularNaoEncontradoException.class, () -> deletar.executar(inexistente));
    }
}
