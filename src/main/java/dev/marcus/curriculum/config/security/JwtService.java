package dev.marcus.curriculum.config.security;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import dev.marcus.curriculum.models.entities.UsuarioEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;

    @Value("${jwt.time.minutes.exp}")
    private int jwtTimeMinutes;

    public String authenticate(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(60L * jwtTimeMinutes);

        UsuarioEntity usuario = ((AuthenticatedUser) authentication.getPrincipal()).usuario();
        String usuarioNome = usuario.getNome();
        String email = usuario.getEmail();
        UUID id = usuario.getId();

        String scope = authentication
            .getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(expiresAt)
            .issuer("dev.marcus.curriculum")
            .subject(email)

            .claim("scope", scope)
            .claim("nome", usuarioNome)
            .claim("userId", id)
        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
