package com.daniel.wec_ssvp.config;

import com.daniel.wec_ssvp.model.entity.TipoUsuario;
import com.daniel.wec_ssvp.model.entity.Usuario;
import com.daniel.wec_ssvp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;

    @Bean
    public CommandLineRunner createAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            if (usuarioRepository.findByEmail(email).isEmpty()) {

                Usuario admin = new Usuario();

                admin.setEmail(email);
                admin.setSenha(passwordEncoder.encode(password));
                admin.setTipoUsuario(TipoUsuario.GESTOR);
                admin.setAtivo(true);

                usuarioRepository.save(admin);

                System.out.println(
                        "Usuário administrador criado com sucesso."
                );
            }
        };
    }
}