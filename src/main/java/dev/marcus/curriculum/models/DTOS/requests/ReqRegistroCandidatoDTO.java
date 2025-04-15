package dev.marcus.curriculum.models.DTOS.requests;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public record ReqRegistroCandidatoDTO(

    @NotBlank(message = "O cpf é obrigatório")
    @CPF(message = "O cpf deve ser válido")
    String cpf,

    @NotBlank(message = "O data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser uma data no passado")
    LocalDate dataNasc,

    @NotBlank(message = "O telefone é obrigatório")
    String telefone
) {

}
