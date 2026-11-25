package com.daniel.wec_ssvp.application.usecase;

import com.daniel.wec_ssvp.application.port.in.assistido.AssistidoCommand;
import com.daniel.wec_ssvp.application.usecase.assistido.AtualizarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.assistido.CriarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.assistido.DeletarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.assistido.ListarAssistidoService;
import com.daniel.wec_ssvp.domain.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.domain.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import com.daniel.wec_ssvp.domain.model.DadosAssistido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AssistidoUseCasesTest {

    private CriarAssistidoService criar;
    private AtualizarAssistidoService atualizar;
    private ListarAssistidoService listar;
    private DeletarAssistidoService deletar;

    private UUID conferencia1;
    private UUID conferencia2;

    @BeforeEach
    void setUp() {
        PortasEmMemoria.ConselhosEmMemoria conselhos = new PortasEmMemoria.ConselhosEmMemoria();
        PortasEmMemoria.ConferenciasEmMemoria conferencias = new PortasEmMemoria.ConferenciasEmMemoria();
        PortasEmMemoria.AssistidosEmMemoria assistidos = new PortasEmMemoria.AssistidosEmMemoria();

        criar = new CriarAssistidoService(assistidos, conferencias);
        atualizar = new AtualizarAssistidoService(assistidos, conferencias);
        listar = new ListarAssistidoService(assistidos);
        deletar = new DeletarAssistidoService(assistidos);

        ConselhoParticular cp = conselhos.salvar(ConselhoParticular.novo("CP", "Mogi", LocalDate.of(1950, 1, 1)));
        conferencia1 = conferencias.salvar(Conferencia.nova("Conf. 1", cp)).getId();
        conferencia2 = conferencias.salvar(Conferencia.nova("Conf. 2", cp)).getId();
    }

    private static DadosAssistido dados(String nome) {
        return new DadosAssistido(
                nome, null, "Rua A, 10", LocalDate.of(1980, 5, 20), "Casado", "Católica", "Pedreiro",
                "Alugada", 1, new BigDecimal("1800.00"), new BigDecimal("1500.00"), new BigDecimal("600.00"), 2,
                null, null, null, null
        );
    }

    @Test
    void criaAssistidoAtivoNaConferencia() {
        Assistido salvo = criar.executar(new AssistidoCommand(dados("Maria"), conferencia1));

        assertNotNull(salvo.getId());
        assertTrue(salvo.isAtivo());
        assertEquals("Maria", salvo.getNome());
        assertEquals("Conf. 1", salvo.getConferencia().getNome());
    }

    @Test
    void naoCriaAssistidoEmConferenciaInexistente() {
        assertThrows(ConferenciaNaoEncontradaException.class,
                () -> criar.executar(new AssistidoCommand(dados("Maria"), UUID.randomUUID())));
        assertTrue(listar.listarTodos().isEmpty());
    }

    @Test
    void atualizarTrocaDadosEConferencia() {
        UUID id = criar.executar(new AssistidoCommand(dados("Maria"), conferencia1)).getId();

        atualizar.executar(id, new AssistidoCommand(dados("Maria da Silva"), conferencia2));

        Assistido atualizado = listar.buscarPorId(id);
        assertEquals("Maria da Silva", atualizado.getNome());
        assertEquals("Conf. 2", atualizado.getConferencia().getNome());
    }

    @Test
    void atualizarAssistidoInexistenteLancaExcecao() {
        assertThrows(AssistidoNaoEncontradoException.class,
                () -> atualizar.executar(UUID.randomUUID(), new AssistidoCommand(dados("X"), conferencia1)));
    }

    @Test
    void deletarInativaMasMantemORegistro() {
        UUID id = criar.executar(new AssistidoCommand(dados("Maria"), conferencia1)).getId();

        assertFalse(deletar.executar(id).isAtivo());
        assertFalse(listar.buscarPorId(id).isAtivo());
        assertEquals(1, listar.listarTodos().size());
    }
}
