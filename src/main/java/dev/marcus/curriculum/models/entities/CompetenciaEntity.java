package dev.marcus.curriculum.models.entities;

import java.util.UUID;

import dev.marcus.curriculum.models.enums.CompetenciaNivelEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "competencia", schema = "curriculum")
@Entity(name = "competencia")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CompetenciaEntity extends BaseEntity{
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompetenciaNivelEnum nivel;

    @ManyToOne
    @JoinColumn(name = "curriculo_id", nullable = false)
    private CurriculoEntity curriculo;
}
