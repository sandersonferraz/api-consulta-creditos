package com.desafio.credito_api.infrastructure.repository;

import com.desafio.credito_api.domain.model.CreditoAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoAuditLogRepository extends JpaRepository<CreditoAuditLog, Long> {
}