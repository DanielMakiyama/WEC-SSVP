package com.daniel.wec_ssvp.application.usecase;

import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.CodificadorSenhaPort;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.GeradorTokenPort;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import com.daniel.wec_ssvp.domain.model.Usuario;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementações das portas de saída em memória, só para testes.
 * Mostram na prática que o núcleo roda sem banco de dados e sem Spring.
 */
public final class PortasEmMemoria {

    private PortasEmMemoria() {
    }

    public static class ConselhosEmMemoria implements ConselhoParticularRepositoryPort {

        private final Map<UUID, ConselhoParticular> dados = new LinkedHashMap<>();

        @Override
        public ConselhoParticular salvar(ConselhoParticular c) {
            UUID id = c.getId() != null ? c.getId() : UUID.randomUUID();
            ConselhoParticular salvo = new ConselhoParticular(
                    id, c.getNome(), c.getCidade(), c.getDataFundacao(), c.isAtivo());
            dados.put(id, salvo);
            return salvo;
        }

        @Override
        public Optional<ConselhoParticular> buscarPorId(UUID id) {
            return Optional.ofNullable(dados.get(id));
        }

        @Override
        public Optional<ConselhoParticular> buscarPorNome(String nome) {
            return dados.values().stream().filter(c -> c.getNome().equals(nome)).findFirst();
        }

        @Override
        public List<ConselhoParticular> listarTodos() {
            return new ArrayList<>(dados.values());
        }
    }

    public static class ConferenciasEmMemoria implements ConferenciaRepositoryPort {

        private final Map<UUID, Conferencia> dados = new LinkedHashMap<>();

        @Override
        public Conferencia salvar(Conferencia c) {
            UUID id = c.getId() != null ? c.getId() : UUID.randomUUID();
            Conferencia salva = new Conferencia(id, c.getNome(), c.isAtivo(), c.getConselhoParticular());
            dados.put(id, salva);
            return salva;
        }

        @Override
        public Optional<Conferencia> buscarPorId(UUID id) {
            return Optional.ofNullable(dados.get(id));
        }

        @Override
        public Optional<Conferencia> buscarPorNomeEConselho(String nome, UUID conselhoParticularId) {
            return dados.values().stream()
                    .filter(c -> c.getNome().equals(nome))
                    .filter(c -> c.getConselhoParticular().getId().equals(conselhoParticularId))
                    .findFirst();
        }

        @Override
        public List<Conferencia> listarTodas() {
            return new ArrayList<>(dados.values());
        }

        @Override
        public List<Conferencia> listarPorConselho(UUID conselhoParticularId) {
            return dados.values().stream()
                    .filter(c -> c.getConselhoParticular().getId().equals(conselhoParticularId))
                    .toList();
        }
    }

    public static class AssistidosEmMemoria implements AssistidoRepositoryPort {

        private final Map<UUID, Assistido> dados = new LinkedHashMap<>();

        @Override
        public Assistido salvar(Assistido a) {
            UUID id = a.getId() != null ? a.getId() : UUID.randomUUID();
            Assistido salvo = new Assistido(id, a.getDados(), a.getConferencia(), a.isAtivo());
            dados.put(id, salvo);
            return salvo;
        }

        @Override
        public Optional<Assistido> buscarPorId(UUID id) {
            return Optional.ofNullable(dados.get(id));
        }

        @Override
        public List<Assistido> listarTodos() {
            return new ArrayList<>(dados.values());
        }
    }

    public static class UsuariosEmMemoria implements UsuarioRepositoryPort {

        private final Map<UUID, Usuario> dados = new LinkedHashMap<>();

        @Override
        public Usuario salvar(Usuario u) {
            UUID id = u.getId() != null ? u.getId() : UUID.randomUUID();
            Usuario salvo = new Usuario(id, u.getEmail(), u.getSenhaHash(), u.getTipoUsuario(), u.isAtivo());
            dados.put(id, salvo);
            return salvo;
        }

        @Override
        public Optional<Usuario> buscarPorEmail(String email) {
            return dados.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
        }
    }

    /** "Criptografia" previsível para os testes conferirem o hash. */
    public static class CodificadorFalso implements CodificadorSenhaPort {

        @Override
        public String codificar(String senha) {
            return "hash:" + senha;
        }

        @Override
        public boolean confere(String senha, String senhaHash) {
            return codificar(senha).equals(senhaHash);
        }
    }

    public static class GeradorTokenFalso implements GeradorTokenPort {

        @Override
        public String gerar(Usuario usuario) {
            return "token-" + usuario.getEmail() + "-" + usuario.getTipoUsuario();
        }
    }
}
