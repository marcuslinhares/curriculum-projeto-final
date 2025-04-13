package dev.marcus.curriculum.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.marcus.curriculum.models.entities.RegraEntity;
import dev.marcus.curriculum.models.enums.RegraNomeEnum;

public interface RegraRepository extends JpaRepository<RegraEntity, UUID>{
    Optional<RegraEntity> findByNome(RegraNomeEnum nome);
}
