package dev.marcus.curriculum.controllers.impls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.controllers.CurriculoController;
import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;
import dev.marcus.curriculum.services.CurriculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/curriculos")
public class CurriculoControllerImpl implements CurriculoController{
    private final CurriculoService curriculoService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATO')")
    public ResponseEntity<ResRegistroCurriculoDTO> save(
        @Valid @RequestBody ReqRegistroCurriculoDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        var response = curriculoService.save(dto);
        var uri = uriBuilder.path("/curriculo/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @GetMapping(params = {"page", "size"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<ResRegistroCurriculoDTO>> findAll(
        @RequestParam int page, @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            this.curriculoService.findAll(PageRequest.of(page, size))
        );
    }

    @Override
    @GetMapping(path = "/loged-user")
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATO')")
    public ResponseEntity<ResRegistroCurriculoDTO> findByLogedUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
            this.curriculoService.findByLogedUser()
        ); 
    }
}
