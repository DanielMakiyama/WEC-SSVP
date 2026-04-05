package com.daniel.wec_ssvp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(String email, String role) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
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
        return extrairEmail(token).equals(email) && !extrairClaims(token).getExpiration().before(new Date());
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
