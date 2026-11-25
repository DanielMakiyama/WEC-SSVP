package com.daniel.wec_ssvp.config;

import com.daniel.wec_ssvp.application.port.in.assistido.AtualizarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.assistido.CriarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.assistido.DeletarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.assistido.ListarAssistidoUseCase;
import com.daniel.wec_ssvp.application.port.in.conferencia.CriarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.in.conferencia.DeletarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.in.conferencia.ListarConferenciaUseCase;
import com.daniel.wec_ssvp.application.port.in.conselho.CriarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.in.conselho.DeletarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.in.conselho.ListarConselhoParticularUseCase;
import com.daniel.wec_ssvp.application.port.in.usuario.AutenticarUsuarioUseCase;
import com.daniel.wec_ssvp.application.port.in.usuario.CriarUsuarioUseCase;
import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.CodificadorSenhaPort;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.application.port.out.GeradorTokenPort;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.application.usecase.assistido.AtualizarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.assistido.CriarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.assistido.DeletarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.assistido.ListarAssistidoService;
import com.daniel.wec_ssvp.application.usecase.conferencia.CriarConferenciaService;
import com.daniel.wec_ssvp.application.usecase.conferencia.DeletarConferenciaService;
import com.daniel.wec_ssvp.application.usecase.conferencia.ListarConferenciaService;
import com.daniel.wec_ssvp.application.usecase.conselho.CriarConselhoParticularService;
import com.daniel.wec_ssvp.application.usecase.conselho.DeletarConselhoParticularService;
import com.daniel.wec_ssvp.application.usecase.conselho.ListarConselhoParticularService;
import com.daniel.wec_ssvp.application.usecase.usuario.AutenticarUsuarioService;
import com.daniel.wec_ssvp.application.usecase.usuario.CriarUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Liga o núcleo aos adaptadores.
 *
 * Os casos de uso não têm anotações do Spring: são classes Java comuns.
 * É aqui que o Spring os instancia, entregando os adaptadores que
 * implementam as portas de saída (inversão de dependência).
 */
@Configuration
public class UseCaseConfig {

    // ---------- Conselho Particular ----------

    @Bean
    public CriarConselhoParticularUseCase criarConselhoParticularUseCase(ConselhoParticularRepositoryPort conselhos) {
        return new CriarConselhoParticularService(conselhos);
    }

    @Bean
    public ListarConselhoParticularUseCase listarConselhoParticularUseCase(ConselhoParticularRepositoryPort conselhos) {
        return new ListarConselhoParticularService(conselhos);
    }

    @Bean
    public DeletarConselhoParticularUseCase deletarConselhoParticularUseCase(ConselhoParticularRepositoryPort conselhos) {
        return new DeletarConselhoParticularService(conselhos);
    }

    // ---------- Conferência ----------

    @Bean
    public CriarConferenciaUseCase criarConferenciaUseCase(ConferenciaRepositoryPort conferencias,
                                                           ConselhoParticularRepositoryPort conselhos) {
        return new CriarConferenciaService(conferencias, conselhos);
    }

    @Bean
    public ListarConferenciaUseCase listarConferenciaUseCase(ConferenciaRepositoryPort conferencias) {
        return new ListarConferenciaService(conferencias);
    }

    @Bean
    public DeletarConferenciaUseCase deletarConferenciaUseCase(ConferenciaRepositoryPort conferencias) {
        return new DeletarConferenciaService(conferencias);
    }

    // ---------- Assistido ----------

    @Bean
    public CriarAssistidoUseCase criarAssistidoUseCase(AssistidoRepositoryPort assistidos,
                                                       ConferenciaRepositoryPort conferencias) {
        return new CriarAssistidoService(assistidos, conferencias);
    }

    @Bean
    public AtualizarAssistidoUseCase atualizarAssistidoUseCase(AssistidoRepositoryPort assistidos,
                                                               ConferenciaRepositoryPort conferencias) {
        return new AtualizarAssistidoService(assistidos, conferencias);
    }

    @Bean
    public ListarAssistidoUseCase listarAssistidoUseCase(AssistidoRepositoryPort assistidos) {
        return new ListarAssistidoService(assistidos);
    }

    @Bean
    public DeletarAssistidoUseCase deletarAssistidoUseCase(AssistidoRepositoryPort assistidos) {
        return new DeletarAssistidoService(assistidos);
    }

    // ---------- Usuário ----------

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioRepositoryPort usuarios,
                                                   CodificadorSenhaPort codificadorSenha) {
        return new CriarUsuarioService(usuarios, codificadorSenha);
    }

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(UsuarioRepositoryPort usuarios,
                                                             CodificadorSenhaPort codificadorSenha,
                                                             GeradorTokenPort geradorToken) {
        return new AutenticarUsuarioService(usuarios, codificadorSenha, geradorToken);
    }
}
