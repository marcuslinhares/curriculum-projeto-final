package dev.marcus.curriculum.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.marcus.curriculum.models.DTOS.responses.SituacaoCountDTO;
import dev.marcus.curriculum.models.entities.CandidatoEntity;

import java.util.List;
import java.util.Optional;

public interface CandidatoRepository extends JpaRepository<CandidatoEntity, UUID>{
    Optional<CandidatoEntity> findByCpf(String cpf);
    Optional<CandidatoEntity> findByUsuario_Id(UUID id);

    @Query("""
        SELECT new dev.marcus.curriculum.models.DTOS.responses.SituacaoCountDTO(
            c.situacao, COUNT(c)
        ) FROM candidato c GROUP BY c.situacao    
    """)
    List<SituacaoCountDTO> countCandidatoSituacao();

}
