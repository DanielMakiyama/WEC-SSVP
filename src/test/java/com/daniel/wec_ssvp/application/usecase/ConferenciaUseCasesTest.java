package com.daniel.wec_ssvp.application.usecase;

import com.daniel.wec_ssvp.application.port.in.conferencia.CriarConferenciaUseCase;
import com.daniel.wec_ssvp.application.usecase.conferencia.CriarConferenciaService;
import com.daniel.wec_ssvp.application.usecase.conferencia.DeletarConferenciaService;
import com.daniel.wec_ssvp.application.usecase.conferencia.ListarConferenciaService;
import com.daniel.wec_ssvp.domain.exception.ConferenciaJaExisteException;
import com.daniel.wec_ssvp.domain.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.domain.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConferenciaUseCasesTest {

    private PortasEmMemoria.ConselhosEmMemoria conselhos;
    private CriarConferenciaService criar;
    private ListarConferenciaService listar;
    private DeletarConferenciaService deletar;

    private UUID conselhoA;
    private UUID conselhoB;

    @BeforeEach
    void setUp() {
        conselhos = new PortasEmMemoria.ConselhosEmMemoria();
        PortasEmMemoria.ConferenciasEmMemoria conferencias = new PortasEmMemoria.ConferenciasEmMemoria();
        criar = new CriarConferenciaService(conferencias, conselhos);
        listar = new ListarConferenciaService(conferencias);
        deletar = new DeletarConferenciaService(conferencias);

        conselhoA = conselhos.salvar(ConselhoParticular.novo("CP A", "Mogi", LocalDate.of(1950, 1, 1))).getId();
        conselhoB = conselhos.salvar(ConselhoParticular.novo("CP B", "Suzano", LocalDate.of(1960, 1, 1))).getId();
    }

    @Test
    void criaConferenciaVinculadaAoConselho() {
        Conferencia salva = criar.executar(new CriarConferenciaUseCase.Command("Conf. São José", conselhoA));

        assertNotNull(salva.getId());
        assertTrue(salva.isAtivo());
        assertEquals(conselhoA, salva.getConselhoParticular().getId());
    }

    @Test
    void naoCriaConferenciaEmConselhoInexistente() {
        assertThrows(ConselhoParticularNaoEncontradoException.class,
                () -> criar.executar(new CriarConferenciaUseCase.Command("Conf. X", UUID.randomUUID())));
    }

    @Test
    void naoPermiteNomeRepetidoNoMesmoConselho() {
        criar.executar(new CriarConferenciaUseCase.Command("Conf. São José", conselhoA));

        assertThrows(ConferenciaJaExisteException.class,
                () -> criar.executar(new CriarConferenciaUseCase.Command("Conf. São José", conselhoA)));
    }

    @Test
    void permiteMesmoNomeEmConselhosDiferentes() {
        criar.executar(new CriarConferenciaUseCase.Command("Conf. São José", conselhoA));

        assertDoesNotThrow(
                () -> criar.executar(new CriarConferenciaUseCase.Command("Conf. São José", conselhoB)));
    }

    @Test
    void listaApenasAsConferenciasDoConselhoInformado() {
        criar.executar(new CriarConferenciaUseCase.Command("Conf. 1", conselhoA));
        criar.executar(new CriarConferenciaUseCase.Command("Conf. 2", conselhoA));
        criar.executar(new CriarConferenciaUseCase.Command("Conf. 3", conselhoB));

        assertEquals(2, listar.listarPorConselho(conselhoA).size());
        assertEquals(1, listar.listarPorConselho(conselhoB).size());
        assertEquals(3, listar.listarTodas().size());
    }

    @Test
    void deletarInativaAConferencia() {
        UUID id = criar.executar(new CriarConferenciaUseCase.Command("Conf. 1", conselhoA)).getId();

        assertFalse(deletar.executar(id).isAtivo());
        assertFalse(listar.buscarPorId(id).isAtivo());
    }

    @Test
    void buscarConferenciaInexistenteLancaExcecao() {
        assertThrows(ConferenciaNaoEncontradaException.class, () -> listar.buscarPorId(UUID.randomUUID()));
    }
}
