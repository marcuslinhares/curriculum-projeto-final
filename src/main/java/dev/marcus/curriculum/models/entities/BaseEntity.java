package dev.marcus.curriculum.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class BaseEntity {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_criacao", updatable = false)
  private LocalDateTime dataCriacao;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "data_atualizacao")
  private LocalDateTime dataAtualizacao;

  @PrePersist
  protected void onCreate() {
    dataCriacao = LocalDateTime.now();
    dataAtualizacao = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    dataAtualizacao = LocalDateTime.now();
  }
}
