package dev.marcus.curriculum.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.marcus.curriculum.config.security.AuthenticatedUser;
import dev.marcus.curriculum.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
            .map(AuthenticatedUser::new)
            .orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado na base de dados!"
            )
        );
    }
}
