package br.com.barbearia.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * Componente responsável por criar, assinar e gerenciar os tokens JWT
 * para autenticação Stateless (sem estado).
 * Localizado no Microsserviço de Usuários (Auth Service).
 */
@Component
public class JwtTokenProvider {

    // Chave secreta lida do application.properties
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // Tempo de expiração: 7 dias em milissegundos
    private final long jwtExpirationInMs = 7 * 24 * 60 * 60 * 1000;

    /**
     * Gera o token JWT baseado em um objeto Authentication (usado para OAuth2 ou login tradicional).
     * @param authentication Objeto de autenticação do Spring Security.
     * @return Token JWT assinado.
     */
    public String generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        // 1. Define o Subject (Email) e os Claims base
        String email;
        Map<String, Object> claims = new java.util.HashMap<>();

        if (principal instanceof OAuth2User oauth2User) {
            // Caso de OAuth2 (Google, etc.)
            email = oauth2User.getAttribute("email");
            claims.put("name", oauth2User.getAttribute("name"));
            claims.put("picture", oauth2User.getAttribute("picture"));
            // Assume-se que o Role (ROLE_USER) será configurado no UserDetailsServiceImpl
            // ou no OAuth2LoginSuccessHandler

        } else if (principal instanceof UserDetails userDetails) {
            // Caso de Login Tradicional (Email/Senha)
            email = userDetails.getUsername();
            // Claims adicionais podem ser incluídos aqui, se necessário (ex: Role)
            // claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        } else {
            throw new IllegalArgumentException("Principal de autenticação não suportado.");
        }

        // 2. Cria as datas
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // 3. Converte a chave secreta
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        // 4. Constrói o Token JWT
        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims) // Adiciona claims personalizados
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key) // O HS512 é inferido pelo Keys.hmacShaKeyFor
                .compact();
    }
}