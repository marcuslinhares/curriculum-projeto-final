package dev.marcus.curriculum.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.SituacaoCountDTO;
import dev.marcus.curriculum.models.entities.CandidatoEntity;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.models.enums.SituacaoEnum;
import dev.marcus.curriculum.repositories.CandidatoRepository;
import dev.marcus.curriculum.services.impls.CandidatoServiceImpl;

@ExtendWith(MockitoExtension.class)
class CandidatoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private AutenticacaoService autenticacaoService;

    @InjectMocks
    private CandidatoServiceImpl candidatoService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void deveSalvarCandidatoQuandoCpfNaoExiste() {
        ReqRegistroCandidatoDTO dto = new ReqRegistroCandidatoDTO(
            "12345678900",
            LocalDate.of(2000, 1, 1),
            "11999999999"
        );

        UsuarioEntity user = new UsuarioEntity();
        user.setId(UUID.randomUUID());

        when(candidatoRepository.findByCpf(dto.cpf())).thenReturn(Optional.empty());
        when(autenticacaoService.getLoggedUser()).thenReturn(user);

        when(candidatoRepository.save(any())).thenAnswer(invocation -> {
            CandidatoEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID());
            entity.setSituacao(SituacaoEnum.SEM_CURRICULO);
            return entity;
        });

        ResRegistroCandidatoDTO result = candidatoService.save(dto);

        assertEquals(dto.cpf(), result.cpf());
        assertEquals(SituacaoEnum.SEM_CURRICULO, result.situacao());
        verify(candidatoRepository).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {
        ReqRegistroCandidatoDTO dto = new ReqRegistroCandidatoDTO(
            "12345678900",
            LocalDate.of(2000, 1, 1),
            "11999999999"
        );

        when(candidatoRepository.findByCpf(dto.cpf())).thenReturn(Optional.of(new CandidatoEntity()));

        assertThrows(ResponseStatusException.class, () -> candidatoService.save(dto));
    }

    @Test
    void deveAtualizarSituacaoCandidato() {
        UUID id = UUID.randomUUID();
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setId(id);
        candidato.setUsuario(new UsuarioEntity());

        when(candidatoRepository.findById(id)).thenReturn(Optional.of(candidato));
        when(candidatoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResRegistroCandidatoDTO result = candidatoService.updateSituacao(id, SituacaoEnum.APROVADO);

        assertEquals(SituacaoEnum.APROVADO, result.situacao());
    }

    @Test
    void deveLancarExcecaoQuandoCandidatoNaoExisteNaAtualizacao() {
        UUID id = UUID.randomUUID();
        when(candidatoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> candidatoService.updateSituacao(id, SituacaoEnum.APROVADO));
    }

    @Test
    void deveAprovarCandidato() {
        UUID id = UUID.randomUUID();
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setId(id);
        candidato.setUsuario(new UsuarioEntity());

        when(candidatoRepository.findById(id)).thenReturn(Optional.of(candidato));
        when(candidatoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResRegistroCandidatoDTO result = candidatoService.aprovarReprovarCandidato(id, true);

        assertEquals(SituacaoEnum.APROVADO, result.situacao());
    }

    @Test
    void deveReprovarCandidato() {
        UUID id = UUID.randomUUID();
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setId(id);
        candidato.setUsuario(new UsuarioEntity());

        when(candidatoRepository.findById(id)).thenReturn(Optional.of(candidato));
        when(candidatoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResRegistroCandidatoDTO result = candidatoService.aprovarReprovarCandidato(id, false);

        assertEquals(SituacaoEnum.REPROVADO, result.situacao());
    }

    @Test
    void deveRetornarCandidatoDoUsuarioLogado() {
        UsuarioEntity user = new UsuarioEntity();
        user.setId(UUID.randomUUID());
        CandidatoEntity candidato = new CandidatoEntity();
        candidato.setUsuario(user);

        when(autenticacaoService.getLoggedUser()).thenReturn(user);
        when(candidatoRepository.findByUsuario_Id(user.getId())).thenReturn(Optional.of(candidato));

        CandidatoEntity result = candidatoService.findByLogedUser();

        assertEquals(user, result.getUsuario());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoTemCandidato() {
        UsuarioEntity user = new UsuarioEntity();
        user.setId(UUID.randomUUID());

        when(autenticacaoService.getLoggedUser()).thenReturn(user);
        when(candidatoRepository.findByUsuario_Id(user.getId())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> candidatoService.findByLogedUser());
    }

    @Test
    void deveRetornarQuantidadePorSituacao() {
        List<SituacaoCountDTO> lista = List.of(new SituacaoCountDTO(SituacaoEnum.APROVADO, 3L));

        when(candidatoRepository.countCandidatoSituacao()).thenReturn(lista);

        List<SituacaoCountDTO> result = candidatoService.obterQuantidadePorSituacao();

        assertEquals(1, result.size());
        assertEquals(SituacaoEnum.APROVADO, result.get(0).situacao());
        assertEquals(3L, result.get(0).quantidade());
    }
}
