package dev.marcus.curriculum.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.marcus.curriculum.models.entities.CompetenciaEntity;

public interface CompetenciaRepository extends JpaRepository<CompetenciaEntity, UUID>{

}
