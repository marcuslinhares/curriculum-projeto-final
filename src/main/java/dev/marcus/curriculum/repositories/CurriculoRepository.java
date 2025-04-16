package dev.marcus.curriculum.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.marcus.curriculum.models.entities.CurriculoEntity;

public interface CurriculoRepository extends JpaRepository<CurriculoEntity, UUID>{
    Optional<CurriculoEntity> findByCandidatoId(UUID id);
}
