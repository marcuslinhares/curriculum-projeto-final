package dev.marcus.curriculum.services.impls;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import dev.marcus.curriculum.models.mappers.UsuarioMapper;
import dev.marcus.curriculum.repositories.UsuarioRepository;
import dev.marcus.curriculum.services.RegraService;
import dev.marcus.curriculum.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RegraService regraService;

    @Override
    public ResRegistroUsuarioDTO save(ReqRegistroUsuarioDTO dto) {
        var usuarioDb = this.usuarioRepository.findByEmail(dto.email());
        if (usuarioDb.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Este email já esta em uso!"
            );
        }

        var usuario = UsuarioMapper.fromReqRegistroDTOToEntity(dto);
        usuario.setRegra(this.regraService.findByNome(RegraNomeEnum.CANDIDATO));

        return UsuarioMapper.fromEntityToResRegistroDTO(
            this.usuarioRepository.save(usuario));
    }


}
