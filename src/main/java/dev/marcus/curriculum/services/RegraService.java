package dev.marcus.curriculum.services;

import dev.marcus.curriculum.models.entities.RegraEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;

public interface RegraService {
    RegraEntity findByNome(RegraNomeEnum regraNome);
}
