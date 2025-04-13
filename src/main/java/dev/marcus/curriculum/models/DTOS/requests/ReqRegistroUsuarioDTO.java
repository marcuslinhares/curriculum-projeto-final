package dev.marcus.curriculum.models.DTOS.requests;

import dev.marcus.curriculum.validations.SenhasIguais;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SenhasIguais
public record ReqRegistroUsuarioDTO(
    @NotBlank(message = "O nome do novo usuario deve ser informado")
    @Size(max = 256, message = "O nome do novo usuario não deve possir mais que 256 caracteres")
    String nome,

    @NotBlank(message = "O email do novo usuario deve ser informado")
    @Size(max = 256, message = "O email do novo usuario não deve possir mais que 256 caracteres")
    String email,

    @NotBlank(message = "A senha do novo usuario deve ser informado")
    @Size(min = 6, message = "A senha do novo usuario deve possir pelo menos 6 caracteres")
    String senha,

    @NotBlank(message = "A senha de confirmação do novo usuario deve ser informado")
    @Size(min = 6, message = "A senha de confirmação do novo usuario deve possir pelo menos 6 caracteres")
    String confirmaSenha
) {

}
