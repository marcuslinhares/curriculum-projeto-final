package dev.marcus.curriculum.services.impls;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.EscolaridadeCountDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;
import dev.marcus.curriculum.models.entities.CandidatoEntity;
import dev.marcus.curriculum.models.entities.CompetenciaEntity;
import dev.marcus.curriculum.models.entities.CurriculoEntity;
import dev.marcus.curriculum.models.enums.SituacaoEnum;
import dev.marcus.curriculum.models.mappers.CandidatoMapper;
import dev.marcus.curriculum.models.mappers.CompetenciaMapper;
import dev.marcus.curriculum.models.mappers.CurriculoMapper;
import dev.marcus.curriculum.models.mappers.UsuarioMapper;
import dev.marcus.curriculum.repositories.CurriculoRepository;
import dev.marcus.curriculum.services.CandidatoService;
import dev.marcus.curriculum.services.CurriculoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CurriculoServiceImpl implements CurriculoService{
    private final CurriculoRepository curriculoRepository;
    private final CandidatoService candidatoService;

    @Override
    public ResRegistroCurriculoDTO save(ReqRegistroCurriculoDTO dto) {
        var candidato = candidatoService.findByLogedUser();

        if (this.curriculoRepository.findByCandidato_Id(
            candidato.getId()).isPresent()
        ) {
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
        this.candidatoService.updateSituacao(candidato.getId(), SituacaoEnum.AGUARDANDO_ANALISE);

        return curriculoResponseBuilder(candidato, competencias, curriculoSalvo);
    }

    @Override
    public Page<ResRegistroCurriculoDTO> findAll(Pageable pageable) {
        return this.curriculoRepository.curriculosOrdenadosPorSituacao(pageable).map(
            (curriculo) -> {
                return CurriculoMapper.fromEntityToResRegistroDTO(
                    curriculo, CandidatoMapper.fromEntityToResRegistroDTO(
                        curriculo.getCandidato(),
                        UsuarioMapper.fromEntityToResRegistroDTO(
                            curriculo.getCandidato().getUsuario()
                        )
                    ), curriculo.getCompetencias().stream().map(
                        CompetenciaMapper::fromEntityToResRegistroDTO
                    ).toList()
                );
            }
        );
    }

    @Override
    public ResRegistroCurriculoDTO findByLogedUser() {
        var candidato = this.candidatoService.findByLogedUser();

        var curriculo = this.curriculoRepository.findByCandidato_Id(candidato.getId()).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "O candidato não cadastrou seu currículo"
            )
        );
            
        var competencias = curriculo.getCompetencias();

        return this.curriculoResponseBuilder(
            candidato,
            competencias,
            curriculo
        );
    }

    private ResRegistroCurriculoDTO curriculoResponseBuilder(
        CandidatoEntity candidato, List<CompetenciaEntity> competencias, CurriculoEntity curriculo
    ){
        var candidatoResponse = CandidatoMapper.fromEntityToResRegistroDTO(
            candidato, UsuarioMapper.fromEntityToResRegistroDTO(candidato.getUsuario())
        );
        var competenciasResponse = curriculo.getCompetencias().stream()
            .map(CompetenciaMapper::fromEntityToResRegistroDTO)
        .toList();

        return CurriculoMapper.fromEntityToResRegistroDTO(
            curriculo, candidatoResponse, competenciasResponse
        );
    }

    @Override
    public ResRegistroCurriculoDTO findById(UUID id) {
        CurriculoEntity curriculo = this.curriculoRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Curriculo não existe na base de dados!"
            )
        );

        return CurriculoMapper.fromEntityToResRegistroDTO(
            curriculo,
            CandidatoMapper.fromEntityToResRegistroDTO(
                curriculo.getCandidato(), 
                UsuarioMapper.fromEntityToResRegistroDTO(curriculo.getCandidato().getUsuario()) 
            ),
            curriculo.getCompetencias().stream().map(
                CompetenciaMapper::fromEntityToResRegistroDTO
            ).toList()
        );
    }

    @Override
    public List<EscolaridadeCountDTO> obterQuantidadePorEscolaridade() {
        return this.curriculoRepository.countCurriculosByEscolaridade();
    }
}
