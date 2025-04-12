package dev.marcus.curriculum.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.marcus.curriculum.models.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID>{
    Optional<UsuarioEntity> findByEmail(String email);
}
