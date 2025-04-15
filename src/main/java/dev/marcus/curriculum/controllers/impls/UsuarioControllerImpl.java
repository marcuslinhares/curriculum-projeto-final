package dev.marcus.curriculum.controllers.impls;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.controllers.UsuarioController;
import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/usuarios")
public class UsuarioControllerImpl implements UsuarioController{
  private final UsuarioService usuarioService;

  @Override
  @PostMapping
  public ResponseEntity<ResRegistroUsuarioDTO> save(
      @Valid @RequestBody ReqRegistroUsuarioDTO dto,
      UriComponentsBuilder uriBuilder
    ) {
        var userResponse = usuarioService.save(dto);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(userResponse.id()).toUri();
        return ResponseEntity.created(uri).body(userResponse);
    }
}
