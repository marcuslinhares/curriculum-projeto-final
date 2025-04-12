package dev.marcus.curriculum.models.entities;

import java.util.List;
import java.util.UUID;

import dev.marcus.curriculum.models.enums.RegraNomeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "regra")
@EqualsAndHashCode(of = "id")
public class RegraEntity {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private RegraNomeEnum nome; 

  @OneToMany(mappedBy = "regra", orphanRemoval = true)
  private List<UsuarioEntity> usuarios;
}
