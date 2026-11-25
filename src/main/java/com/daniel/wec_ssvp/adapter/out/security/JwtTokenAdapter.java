package com.daniel.wec_ssvp.adapter.out.security;

import com.daniel.wec_ssvp.application.port.out.GeradorTokenPort;
import com.daniel.wec_ssvp.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Adaptador de saída: emite tokens JWT (porta GeradorTokenPort)
 * e oferece a leitura do token para o filtro de autenticação.
 */
@Component
public class JwtTokenAdapter implements GeradorTokenPort {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public String gerar(Usuario usuario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("role", usuario.getTipoUsuario().name())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getSignInKey())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public String extrairRole(String token) {
        return extrairClaims(token).get("role", String.class);
    }

    public boolean tokenValido(String token, String email) {
        return extrairEmail(token).equals(email)
                && !extrairClaims(token).getExpiration().before(new Date());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
