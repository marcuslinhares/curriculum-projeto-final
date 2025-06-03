package dev.marcus.curriculum.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCompetenciaDTO;
import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.entities.CandidatoEntity;
import dev.marcus.curriculum.models.entities.CurriculoEntity;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.models.enums.CompetenciaNivelEnum;
import dev.marcus.curriculum.models.enums.EscolaridadeEnum;
import dev.marcus.curriculum.models.enums.SituacaoEnum;
import dev.marcus.curriculum.repositories.CurriculoRepository;
import dev.marcus.curriculum.services.impls.CurriculoServiceImpl;

@ExtendWith(MockitoExtension.class)
class CurriculoServiceImplTest {

    @Mock
    private CurriculoRepository curriculoRepository;

    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CurriculoServiceImpl curriculoService;

    private CandidatoEntity candidato;

    @BeforeEach
    void setUp() {
        candidato = new CandidatoEntity();
        candidato.setId(UUID.randomUUID());
    
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(UUID.randomUUID());
    
        candidato.setUsuario(usuario);
    }

    @Test
    void save_DeveSalvarCurriculo_QuandoNaoExisteCurriculo() {
        ReqRegistroCompetenciaDTO compDTO = new ReqRegistroCompetenciaDTO("Java", CompetenciaNivelEnum.INTERMEDIARIO);
        ReqRegistroCurriculoDTO dto = new ReqRegistroCurriculoDTO(
                EscolaridadeEnum.MEDIO_COMPLETO,
                "Desenvolvedor Backend",
                List.of(compDTO)
        );
    
        CurriculoEntity curriculoSalvo = new CurriculoEntity();
        curriculoSalvo.setId(UUID.randomUUID());
        curriculoSalvo.setEscolaridade(EscolaridadeEnum.MEDIO_COMPLETO);
        curriculoSalvo.setFuncao("Desenvolvedor");
        curriculoSalvo.setCompetencias(List.of());
    
        when(candidatoService.findByLogedUser()).thenReturn(candidato);
        when(curriculoRepository.findByCandidato_Id(candidato.getId())).thenReturn(Optional.empty());
        when(curriculoRepository.save(any())).thenReturn(curriculoSalvo);
    
        var result = curriculoService.save(dto);
    
        assertNotNull(result);
        assertEquals(curriculoSalvo.getId(), result.id());
        verify(candidatoService).updateSituacao(candidato.getId(), SituacaoEnum.AGUARDANDO_ANALISE);
    }

    @Test
    void save_DeveLancarExcecao_QuandoCurriculoJaExiste() {
        ReqRegistroCurriculoDTO dto = new ReqRegistroCurriculoDTO(
                EscolaridadeEnum.MEDIO_COMPLETO,
                "Desenvolvedor",
                List.of(new ReqRegistroCompetenciaDTO("Java", CompetenciaNivelEnum.INICIANTE))
        );

        when(candidatoService.findByLogedUser()).thenReturn(candidato);
        when(curriculoRepository.findByCandidato_Id(candidato.getId()))
                .thenReturn(Optional.of(new CurriculoEntity()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            curriculoService.save(dto);
        });

        assertEquals(409, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("O candidato já possui um currículo"));
    }

    @Test
    void findByLogedUser_DeveRetornarCurriculo_QuandoExistente() {
        CurriculoEntity curriculo = new CurriculoEntity();
        curriculo.setId(UUID.randomUUID());
        curriculo.setEscolaridade(EscolaridadeEnum.SUPERIOR_COMPLETO);
        curriculo.setFuncao("Dev");

        curriculo.setCandidato(candidato);
        curriculo.setCompetencias(List.of());

        when(candidatoService.findByLogedUser()).thenReturn(candidato);
        when(curriculoRepository.findByCandidato_Id(candidato.getId())).thenReturn(Optional.of(curriculo));

        var result = curriculoService.findByLogedUser();

        assertNotNull(result);
        assertEquals(curriculo.getId(), result.id());
        assertEquals("Dev", result.funcao());
    }

    @Test
    void findByLogedUser_DeveLancarExcecao_QuandoCurriculoNaoEncontrado() {
        when(candidatoService.findByLogedUser()).thenReturn(candidato);
        when(curriculoRepository.findByCandidato_Id(candidato.getId())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            curriculoService.findByLogedUser();
        });

        assertEquals(404, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("O candidato não cadastrou seu currículo"));
    }
}
