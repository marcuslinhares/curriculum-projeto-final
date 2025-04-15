package dev.marcus.curriculum.services.impls;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.enums.SituacaoEnum;
import dev.marcus.curriculum.models.mappers.CandidatoMapper;
import dev.marcus.curriculum.repositories.CandidatoRepository;
import dev.marcus.curriculum.services.AutenticacaoService;
import dev.marcus.curriculum.services.CandidatoService;
import lombok.RequiredArgsConstructor;

@Service
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
        return CandidatoMapper.fromEntityToResRegistroDTO(candidatoSalvo);
    }
}
