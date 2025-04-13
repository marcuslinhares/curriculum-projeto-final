package dev.marcus.curriculum.services.impls;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.marcus.curriculum.models.entities.RegraEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import dev.marcus.curriculum.repositories.RegraRepository;
import dev.marcus.curriculum.services.RegraService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegraServiceImpl implements RegraService{
    private final RegraRepository regraRepository;

    @Override
    public RegraEntity findByNome(RegraNomeEnum nome) {
        return this.regraRepository.findByNome(nome).orElseThrow(
            () -> {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "A regra de nome informado não existe na base de dados"
                );
            }
        );
    }

}
