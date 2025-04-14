package dev.marcus.curriculum.validations.validators;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.validations.SenhasIguais;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhasIguaisValidator  implements ConstraintValidator<SenhasIguais, ReqRegistroUsuarioDTO> {
    
    @Override
    public boolean isValid(ReqRegistroUsuarioDTO dto, ConstraintValidatorContext context) {
        if (!dto.senha().equals(dto.confirmaSenha())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "As senhas não coincidem"
            ).addPropertyNode("confirma_senha").addConstraintViolation();
            return false;
        }

        return true;
    }
}
