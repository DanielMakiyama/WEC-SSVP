package com.daniel.wec_ssvp.config;

import com.daniel.wec_ssvp.security.CustomAccessDeniedHandler;
import com.daniel.wec_ssvp.security.CustomAuthenticationEntryPoint;
import com.daniel.wec_ssvp.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                // LIBERA CORS PARA FRONTEND
                .cors(cors -> {})

                // DESABILITA CSRF
                .csrf(csrf -> csrf.disable())

                // JWT = STATELESS
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // TRATAMENTO DE ERROS
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                // REGRAS DE ACESSO
                .authorizeHttpRequests(auth -> auth

                        // AUTH
                        .requestMatchers("/auth/**").permitAll()

                        // CONSELHOS
                        .requestMatchers("/conselhos/criar").hasAuthority("GESTOR")
                        .requestMatchers("/conselhos/deletar/**").hasAuthority("GESTOR")
                        .requestMatchers("/conselhos/buscar").permitAll()

                        // CONFERENCIAS
                        .requestMatchers("/conferencias/criar").hasAuthority("GESTOR")
                        .requestMatchers("/conferencias/deletar/**").hasAuthority("GESTOR")
                        .requestMatchers("/conferencias/buscar").permitAll()

                        // ASSISTIDOS
                        .requestMatchers("/assistidos/criar").permitAll()
                        .requestMatchers("/assistidos/buscar").permitAll()
                        .requestMatchers("/assistidos/atualizar/**").permitAll()
                        .requestMatchers("/assistidos/deletar/**").hasAuthority("GESTOR")

                        // QUALQUER OUTRA ROTA
                        .anyRequest().authenticated()

                )


                // JWT FILTER
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}