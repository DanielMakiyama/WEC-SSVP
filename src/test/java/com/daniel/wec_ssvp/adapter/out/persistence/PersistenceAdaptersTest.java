package com.daniel.wec_ssvp.adapter.out.persistence;

import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import com.daniel.wec_ssvp.domain.model.DadosAssistido;
import com.daniel.wec_ssvp.domain.model.TipoUsuario;
import com.daniel.wec_ssvp.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa os adaptadores de persistência contra um banco H2 em memória.
 *
 * O @DataJpaTest sobe só a parte de JPA (sem web e sem segurança).
 * Os testes usam as PORTAS, como o núcleo faria, e confirmam que o
 * PersistenceMapper grava e lê cada campo corretamente.
 */
@DataJpaTest
@Import({
        ConselhoParticularPersistenceAdapter.class,
        ConferenciaPersistenceAdapter.class,
        AssistidoPersistenceAdapter.class,
        UsuarioPersistenceAdapter.class
})
class PersistenceAdaptersTest {

    @Autowired
    private ConselhoParticularRepositoryPort conselhos;

    @Autowired
    private ConferenciaRepositoryPort conferencias;

    @Autowired
    private AssistidoRepositoryPort assistidos;

    @Autowired
    private UsuarioRepositoryPort usuarios;

    @Autowired
    private TestEntityManager em;

    /**
     * Grava tudo no banco e esvazia o cache do Hibernate,
     * para que a próxima leitura venha de fato do banco.
     */
    private void limparCache() {
        em.flush();
        em.clear();
    }

    private ConselhoParticular novoConselho(String nome) {
        return conselhos.salvar(ConselhoParticular.novo(nome, "Mogi das Cruzes", LocalDate.of(1950, 3, 10)));
    }

    private static DadosAssistido dadosCompletos(String nome) {
        return new DadosAssistido(
                nome, "José", "Rua das Flores, 100", LocalDate.of(1975, 8, 15),
                "Casado", "Católica", "Pedreiro",
                "Alugada", 2,
                new BigDecimal("2500.00"), new BigDecimal("2100.50"), new BigDecimal("800.00"), 3,
                "Precisa de Crisma", "Participa da missa", "Hipertensão", "Família com 3 filhos"
        );
    }

    // ---------- Conselho Particular ----------

    @Test
    void conselhoIdaEVoltaPeloBanco() {
        UUID id = novoConselho("CP Mogi").getId();
        limparCache();

        ConselhoParticular lido = conselhos.buscarPorId(id).orElseThrow();

        assertEquals("CP Mogi", lido.getNome());
        assertEquals("Mogi das Cruzes", lido.getCidade());
        assertEquals(LocalDate.of(1950, 3, 10), lido.getDataFundacao());
        assertTrue(lido.isAtivo());
    }

    @Test
    void buscaConselhoPorNome() {
        novoConselho("CP Mogi");
        limparCache();

        assertTrue(conselhos.buscarPorNome("CP Mogi").isPresent());
        assertTrue(conselhos.buscarPorNome("CP Inexistente").isEmpty());
    }

    // ---------- Conferência ----------

    @Test
    void conferenciaVoltaComOConselhoVinculado() {
        ConselhoParticular cp = novoConselho("CP Mogi");
        UUID id = conferencias.salvar(Conferencia.nova("Conf. São José", cp)).getId();
        limparCache();

        Conferencia lida = conferencias.buscarPorId(id).orElseThrow();

        assertEquals("Conf. São José", lida.getNome());
        assertTrue(lida.isAtivo());
        assertEquals(cp.getId(), lida.getConselhoParticular().getId());
        assertEquals("CP Mogi", lida.getConselhoParticular().getNome());
    }

    @Test
    void consultasDeConferenciaFiltramPorConselho() {
        ConselhoParticular cpA = novoConselho("CP A");
        ConselhoParticular cpB = novoConselho("CP B");
        conferencias.salvar(Conferencia.nova("Conf. 1", cpA));
        conferencias.salvar(Conferencia.nova("Conf. 2", cpA));
        conferencias.salvar(Conferencia.nova("Conf. 1", cpB));
        limparCache();

        assertEquals(2, conferencias.listarPorConselho(cpA.getId()).size());
        assertEquals(1, conferencias.listarPorConselho(cpB.getId()).size());
        assertEquals(3, conferencias.listarTodas().size());
        assertTrue(conferencias.buscarPorNomeEConselho("Conf. 2", cpA.getId()).isPresent());
        assertTrue(conferencias.buscarPorNomeEConselho("Conf. 2", cpB.getId()).isEmpty());
    }

    // ---------- Assistido ----------

    @Test
    void assistidoGravaELeTodosOsCampos() {
        ConselhoParticular cp = novoConselho("CP Mogi");
        Conferencia conf = conferencias.salvar(Conferencia.nova("Conf. São José", cp));
        DadosAssistido dados = dadosCompletos("Maria da Silva");

        UUID id = assistidos.salvar(Assistido.novo(dados, conf)).getId();
        limparCache();

        Assistido lido = assistidos.buscarPorId(id).orElseThrow();

        // DadosAssistido é um record: o equals compara os 17 campos de uma vez
        assertEquals(dados, lido.getDados());
        assertTrue(lido.isAtivo());
        assertEquals("Conf. São José", lido.getConferencia().getNome());
        assertEquals("CP Mogi", lido.getConferencia().getConselhoParticular().getNome());
    }

    @Test
    void salvarAssistidoExistenteAtualizaSemDuplicar() {
        ConselhoParticular cp = novoConselho("CP Mogi");
        Conferencia conf1 = conferencias.salvar(Conferencia.nova("Conf. 1", cp));
        Conferencia conf2 = conferencias.salvar(Conferencia.nova("Conf. 2", cp));
        UUID id = assistidos.salvar(Assistido.novo(dadosCompletos("Maria"), conf1)).getId();
        limparCache();

        Assistido assistido = assistidos.buscarPorId(id).orElseThrow();
        assistido.atualizar(dadosCompletos("Maria Atualizada"), conf2);
        assistidos.salvar(assistido);
        limparCache();

        assertEquals(1, assistidos.listarTodos().size());
        Assistido lido = assistidos.buscarPorId(id).orElseThrow();
        assertEquals("Maria Atualizada", lido.getNome());
        assertEquals("Conf. 2", lido.getConferencia().getNome());
    }

    @Test
    void inativarAssistidoFicaGravadoNoBanco() {
        ConselhoParticular cp = novoConselho("CP Mogi");
        Conferencia conf = conferencias.salvar(Conferencia.nova("Conf. 1", cp));
        Assistido assistido = assistidos.salvar(Assistido.novo(dadosCompletos("Maria"), conf));

        assistido.inativar();
        assistidos.salvar(assistido);
        limparCache();

        assertFalse(assistidos.buscarPorId(assistido.getId()).orElseThrow().isAtivo());
    }

    // ---------- Usuário ----------

    @Test
    void usuarioIdaEVoltaEBuscaPorEmail() {
        Usuario salvo = usuarios.salvar(Usuario.novo("ana@ssvp.com", "$2a$10$hashFalso", TipoUsuario.GESTOR));
        limparCache();

        Usuario lido = usuarios.buscarPorEmail("ana@ssvp.com").orElseThrow();

        assertNotNull(salvo.getId());
        assertEquals(salvo.getId(), lido.getId());
        assertEquals("$2a$10$hashFalso", lido.getSenhaHash());
        assertEquals(TipoUsuario.GESTOR, lido.getTipoUsuario());
        assertTrue(lido.isAtivo());
        assertTrue(usuarios.buscarPorEmail("ninguem@ssvp.com").isEmpty());
    }
}
