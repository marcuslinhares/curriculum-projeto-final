package dev.marcus.curriculum.services.impls;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import dev.marcus.curriculum.models.mappers.UsuarioMapper;
import dev.marcus.curriculum.repositories.UsuarioRepository;
import dev.marcus.curriculum.services.RegraService;
import dev.marcus.curriculum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RegraService regraService;
    private final PasswordEncoder passwordEncoder;

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
        usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));

        return UsuarioMapper.fromEntityToResRegistroDTO(
            this.usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioEntity findById(UUID id) {
        return this.usuarioRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario com id informado não existe na base de dados"
            )
        );
    }

}
