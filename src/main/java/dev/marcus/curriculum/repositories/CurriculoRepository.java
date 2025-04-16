package dev.marcus.curriculum.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.marcus.curriculum.models.entities.CurriculoEntity;

public interface CurriculoRepository extends JpaRepository<CurriculoEntity, UUID>{
    Optional<CurriculoEntity> findByCandidatoId(UUID id);

    @Query("""
        SELECT c FROM curriculo c
        JOIN c.candidato cand
        ORDER BY
        CASE cand.situacao
            WHEN 'AGUARDANDO_ANALISE' THEN 1
            WHEN 'APROVADO' THEN 2
            WHEN 'REPROVADO' THEN 3
            WHEN 'SEM_CURRICULO' THEN 4
            ELSE 5
        END
    """)
    Page<CurriculoEntity> curriculosOrdenadosPorSituacao(Pageable pageable);

}
