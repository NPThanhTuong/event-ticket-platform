package com.tuong.tickets.mappers;

import com.tuong.tickets.domain.dtos.response.TicketValidationResponseDto;
import com.tuong.tickets.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

	@Mapping(target = "ticketId", source = "ticket.id")
	TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation ticketValidation);
}
