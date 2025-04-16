package dev.marcus.curriculum.services.impls;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;
import dev.marcus.curriculum.models.enums.SituacaoEnum;
import dev.marcus.curriculum.models.mappers.CandidatoMapper;
import dev.marcus.curriculum.models.mappers.CompetenciaMapper;
import dev.marcus.curriculum.models.mappers.CurriculoMapper;
import dev.marcus.curriculum.repositories.CurriculoRepository;
import dev.marcus.curriculum.services.AutenticacaoService;
import dev.marcus.curriculum.services.CandidatoService;
import dev.marcus.curriculum.services.CurriculoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CurriculoServiceImpl implements CurriculoService{
    private final CurriculoRepository curriculoRepository;
    private final AutenticacaoService autenticacaoService;
    private final CandidatoService candidatoService;

    @Override
    public ResRegistroCurriculoDTO save(ReqRegistroCurriculoDTO dto) {
        var candidato = autenticacaoService.getLoggedUser().getCandidato();

        if (this.curriculoRepository.findByCandidatoId(candidato.getId()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "O candidato já possui um currículo cadastrado"
            );
        }

        var curriculo = CurriculoMapper.fromReqRegistroDTOToEntity(dto);
        var competencias = dto.competencias().stream()
            .map(CompetenciaMapper::fromReqRegistroDTOToEntity)
            .peek(c -> c.setCurriculo(curriculo))
        .toList();
        
        curriculo.setCandidato(candidato);
        curriculo.setCompetencias(competencias);

        var curriculoSalvo = this.curriculoRepository.save(curriculo);
        this.candidatoService.UpdateSituacao(candidato.getId(), SituacaoEnum.AGUARDANDO_ANALISE);

        var candidatoResponse = CandidatoMapper.fromEntityToResRegistroDTO(candidato);
        var competenciasResponse = curriculoSalvo.getCompetencias().stream()
            .map(CompetenciaMapper::fromEntityToResRegistroDTO)
        .toList();

        return CurriculoMapper.fromEntityToResRegistroDTO(
            curriculoSalvo, candidatoResponse, competenciasResponse
        );
    }
}
