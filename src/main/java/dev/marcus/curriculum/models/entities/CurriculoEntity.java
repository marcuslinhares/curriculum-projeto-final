package dev.marcus.curriculum.models.entities;

import java.util.UUID;

import dev.marcus.curriculum.models.enums.EscolaridadeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "curriculo", schema = "curriculum")
@Entity(name = "curriculo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CurriculoEntity extends BaseEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EscolaridadeEnum escolaridade;

    @Column(nullable = false)
    private String funcao;

    @OneToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    private CandidatoEntity candidato;
}
