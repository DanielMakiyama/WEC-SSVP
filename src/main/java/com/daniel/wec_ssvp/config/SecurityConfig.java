package com.daniel.wec_ssvp.config;

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
import com.daniel.wec_ssvp.security.CustomAuthenticationEntryPoint;
import com.daniel.wec_ssvp.security.CustomAccessDeniedHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter
            , CustomAuthenticationEntryPoint customAuthenticationEntryPoint
            , CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        //conselho
                        .requestMatchers("/conselhos/criar").hasAuthority("GESTOR")
                        .requestMatchers("/conselhos/deletar/{id}").hasAuthority("GESTOR")
                        .requestMatchers("/conselhos/buscar").permitAll()
                        //conferencias
                        .requestMatchers("/conferencias/criar").hasAuthority("GESTOR")
                        .requestMatchers("/conferencias/buscar").permitAll()
                        .requestMatchers("/conferencias/buscarConselho/{id}").permitAll()
                        .requestMatchers("/conferencias/deletar/{id}").hasAuthority("GESTOR")
                        //assistidos
                        .requestMatchers("/assistidos/criar").permitAll()
                        .requestMatchers("/assistidos/buscar").permitAll().requestMatchers("/assistidos/buscarId/{id}").permitAll()
                        .requestMatchers("/assistidos/deletar/{id}").hasAnyAuthority( "GESTOR")
                        .requestMatchers("/assistidos/atualizar/{id}").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}