package dev.marcus.curriculum.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.marcus.curriculum.models.entities.CandidatoEntity;

import java.util.Optional;

public interface CandidatoRepository extends JpaRepository<CandidatoEntity, UUID>{
    Optional<CandidatoEntity> findByCpf(String cpf);
}
