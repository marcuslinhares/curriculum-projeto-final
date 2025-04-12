package dev.marcus.curriculum.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BaseEntity {

  @Column(name = "created_at", updatable = false)
  private LocalDateTime dataCriacao;

  @Column(name = "updated_at")
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
