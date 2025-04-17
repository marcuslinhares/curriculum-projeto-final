package dev.marcus.curriculum.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtKeyProvider keyProvider;

    @Bean
    SecurityFilterChain filterChainJwtAuth(
        HttpSecurity http,
        CustomBasicAuthFilter filter
    ) throws Exception {
        return http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/docs", "/swagger-ui/**", "/v3/api-docs/**"
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/autenticacao").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(filter, BasicAuthenticationFilter.class)
            .oauth2ResourceServer(conf -> conf.jwt(jwt -> jwt.decoder(jwtDecoder())))
            .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyProvider.getPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(keyProvider.getPublicKey())
            .privateKey(keyProvider.getPrivateKey())
            .build();
        var jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}