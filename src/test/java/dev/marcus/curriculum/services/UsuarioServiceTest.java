package dev.marcus.curriculum.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.entities.RegraEntity;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import dev.marcus.curriculum.models.mappers.UsuarioMapper;
import dev.marcus.curriculum.repositories.UsuarioRepository;
import dev.marcus.curriculum.services.impls.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RegraService regraService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void save_DeveSalvarUsuarioQuandoEmailNaoExiste() {
        var dto = new ReqRegistroUsuarioDTO("Marcus", "marcus@example.com", "senha123", "senha123");

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(regraService.findByNome(RegraNomeEnum.CANDIDATO)).thenReturn(new RegraEntity());
        when(passwordEncoder.encode(dto.senha())).thenReturn("senhaCodificada");

        UsuarioEntity usuarioSalvo = UsuarioMapper.fromReqRegistroDTOToEntity(dto);
        usuarioSalvo.setId(UUID.randomUUID());
        usuarioSalvo.setRegra(new RegraEntity());
        usuarioSalvo.setSenha("senhaCodificada");

        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioSalvo);

        ResRegistroUsuarioDTO response = usuarioService.save(dto);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(usuarioSalvo.getId());
        assertThat(response.nome()).isEqualTo(dto.nome());
        assertThat(response.email()).isEqualTo(dto.email());

        verify(usuarioRepository).findByEmail(dto.email());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void save_DeveLancarExcecaoQuandoEmailJaExiste() {
        var dto = new ReqRegistroUsuarioDTO("Marcus", "marcus@example.com", "senha123", "senha123");

        when(usuarioRepository.findByEmail(dto.email()))
            .thenReturn(Optional.of(new UsuarioEntity()));

        assertThatThrownBy(() -> usuarioService.save(dto))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Este email já esta em uso!");

        verify(usuarioRepository).findByEmail(dto.email());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void findById_DeveRetornarUsuarioQuandoEncontrado() {
        UUID id = UUID.randomUUID();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioEntity resultado = usuarioService.findById(id);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(id);

        verify(usuarioRepository).findById(id);
    }

    @Test
    void findById_DeveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.findById(id))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Usuario com id informado não existe na base de dados");

        verify(usuarioRepository).findById(id);
    }
}
