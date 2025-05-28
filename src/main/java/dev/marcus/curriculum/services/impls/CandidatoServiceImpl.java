package dev.marcus.curriculum.services.impls;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.SituacaoCountDTO;
import dev.marcus.curriculum.models.entities.CandidatoEntity;
import dev.marcus.curriculum.models.enums.SituacaoEnum;
import dev.marcus.curriculum.models.mappers.CandidatoMapper;
import dev.marcus.curriculum.models.mappers.UsuarioMapper;
import dev.marcus.curriculum.repositories.CandidatoRepository;
import dev.marcus.curriculum.services.AutenticacaoService;
import dev.marcus.curriculum.services.CandidatoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidatoServiceImpl implements CandidatoService{
    private final CandidatoRepository candidatoRepository;
    private final AutenticacaoService autenticacaoService;

    @Override
    public ResRegistroCandidatoDTO save(ReqRegistroCandidatoDTO dto) {
        if (this.candidatoRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "O cpf informado já foi cadastrado"
            );
        }
        
        var candidato = CandidatoMapper.fromReqRegistroDTOToEntity(dto);
        candidato.setUsuario(this.autenticacaoService.getLoggedUser());
        candidato.setSituacao(SituacaoEnum.SEM_CURRICULO);
        var candidatoSalvo = this.candidatoRepository.save(candidato);

        return CandidatoMapper.fromEntityToResRegistroDTO(
            candidatoSalvo,
            UsuarioMapper.fromEntityToResRegistroDTO(
                candidatoSalvo.getUsuario()
            )
        );
    }

    @Override
    public ResRegistroCandidatoDTO updateSituacao(UUID candidatoId, SituacaoEnum situacao) {
        var candidato = this.candidatoRepository.findById(candidatoId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "O candidato com id informado não existe na base de dados"
            )
        );

        candidato.setSituacao(situacao);
        return CandidatoMapper.fromEntityToResRegistroDTO(
            this.candidatoRepository.save(candidato),
            UsuarioMapper.fromEntityToResRegistroDTO(candidato.getUsuario())
        );
    }

    @Override
    public ResRegistroCandidatoDTO aprovarReprovarCandidato(UUID candidatoId, boolean aprovado) {
        if (aprovado) {
            return this.updateSituacao(candidatoId, SituacaoEnum.APROVADO);
        }
        return this.updateSituacao(candidatoId, SituacaoEnum.REPROVADO);
    }

    @Override
    public CandidatoEntity findByLogedUser() {
        return this.candidatoRepository.findByUsuario_Id(
            autenticacaoService.getLoggedUser().getId()
        ).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não completou o cadastro"
            )
        );
    }

    @Override
    public List<SituacaoCountDTO> obterQuantidadePorSituacao() {
        return this.candidatoRepository.countCandidatoSituacao();    
    }
}
