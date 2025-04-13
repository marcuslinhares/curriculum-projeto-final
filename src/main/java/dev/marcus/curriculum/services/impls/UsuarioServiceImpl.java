package dev.marcus.curriculum.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import dev.marcus.curriculum.repositories.UsuarioRepository;
import dev.marcus.curriculum.services.RegraService;
import dev.marcus.curriculum.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RegraService regraService;
    private ModelMapper modelMapper;

    @Override
    public ResRegistroUsuarioDTO save(ReqRegistroUsuarioDTO dto) {
        var usuario = modelMapper.map(dto, UsuarioEntity.class);
        usuario.setRegra(regraService.findByNome(RegraNomeEnum.CANDIDATO));
        var usuarioSalvo = usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSalvo, ResRegistroUsuarioDTO.class);
    }
}
