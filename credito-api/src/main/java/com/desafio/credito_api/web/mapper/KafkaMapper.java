package com.desafio.credito_api.web.mapper;

import com.desafio.credito_api.domain.model.CreditoAuditLog;
import com.desafio.credito_api.web.dto.CreditoAuditEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KafkaMapper {
    KafkaMapper INSTANCE = Mappers.getMapper(KafkaMapper.class);

    CreditoAuditLog toEntity(CreditoAuditEventDTO dto);
}
