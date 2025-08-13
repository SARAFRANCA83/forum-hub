package br.com.sfranca.forum.hub.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.secret}")
    private String secret;

    @Value("${forum.jwt.expiration}")
    private Long expiration;

    public String gerarToken(Authentication authentication) {
        // Pega o usuário logado
        String username = authentication.getName();

        // Gera chave secreta a partir do secret configurado
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .setIssuer("API do Fórum") // quem emitiu o token
                .setSubject(username)      // usuário
                .setIssuedAt(agora)        // data de criação
                .setExpiration(dataExpiracao) // data de expiração
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
