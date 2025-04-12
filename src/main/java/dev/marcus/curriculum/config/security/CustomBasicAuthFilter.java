package dev.marcus.curriculum.config.security;

import java.io.IOException;
import java.util.Base64;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomBasicAuthFilter extends OncePerRequestFilter {
  private static final int BASIC_LENGTH = 6;

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal (
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    var isAuthentication = "/autenticacao".equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod());
    var headerAuthorization = request.getHeader("Authorization");

    if (!isAuthentication || headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String basicToken = headerAuthorization.substring(BASIC_LENGTH);
    byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);
    String basicTokenValue = new String(basicTokenDecoded);
    String[] basicAuthsSplit = basicTokenValue.split(":");

    String username = basicAuthsSplit[0];
    String password = basicAuthsSplit[1];

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      var user = userDetailsService.loadUserByUsername(username);

      if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
      }

      var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
