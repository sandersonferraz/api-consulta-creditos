package com.desafio.credito_api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditoAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_credito", nullable = false, unique = true, length = 50)
    private String numeroCredito;
    @Column(name = "numero_nfse", nullable = false, length = 50)
    private String numeroNfse;
    @Column(name = "consulted_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime consultedAt;
}
