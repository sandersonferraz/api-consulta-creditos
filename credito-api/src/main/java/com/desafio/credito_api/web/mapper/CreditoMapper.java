package com.desafio.credito_api.web.mapper;

import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CreditoMapper {

    CreditoMapper INSTANCE = Mappers.getMapper(CreditoMapper.class);

    List<CreditoResponseDTO> toResponseList(List<Credito> creditoList);

    CreditoResponseDTO toResponse(Credito credito);
}
