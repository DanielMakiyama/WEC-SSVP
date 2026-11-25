package com.daniel.wec_ssvp.config;

import com.daniel.wec_ssvp.application.port.in.usuario.CriarUsuarioUseCase;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.domain.model.TipoUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Cria o usuário administrador na inicialização, se ele ainda não existir. */
@Configuration
public class AdminInitializer {

    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;

    @Bean
    public CommandLineRunner createAdmin(
            UsuarioRepositoryPort usuarioRepository,
            CriarUsuarioUseCase criarUsuarioUseCase
    ) {
        return args -> {
            if (usuarioRepository.buscarPorEmail(email).isEmpty()) {
                criarUsuarioUseCase.executar(
                        new CriarUsuarioUseCase.Command(email, password, TipoUsuario.GESTOR)
                );
                log.info("Usuário administrador criado com sucesso.");
            }
        };
    }
}
