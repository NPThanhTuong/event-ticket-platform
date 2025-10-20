package com.tuong.tickets.mappers;

import com.tuong.tickets.domain.dtos.CreateEventRequest;
import com.tuong.tickets.domain.dtos.CreateTicketTypeRequest;
import com.tuong.tickets.domain.dtos.request.CreateEventRequestDto;
import com.tuong.tickets.domain.dtos.request.CreateTicketTypeRequestDto;
import com.tuong.tickets.domain.dtos.response.CreateEventResponseDto;
import com.tuong.tickets.domain.dtos.response.ListEventResponseDto;
import com.tuong.tickets.domain.dtos.response.ListEventTicketTypeResponseDto;
import com.tuong.tickets.domain.entities.Event;
import com.tuong.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

	CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

	CreateEventRequest fromDto(CreateEventRequestDto dto);

	CreateEventResponseDto toDto(Event event);

	ListEventResponseDto toListEventResponseDto(Event event);

	ListEventTicketTypeResponseDto toDto(TicketType ticketType);
}
