package com.tuong.tickets.mappers;

import com.tuong.tickets.domain.dtos.response.GetTicketResponseDto;
import com.tuong.tickets.domain.dtos.response.ListTicketResponseDto;
import com.tuong.tickets.domain.dtos.response.ListTicketResponseTicketTypeDto;
import com.tuong.tickets.domain.entities.Ticket;
import com.tuong.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
	ListTicketResponseTicketTypeDto toListTicketResponseTicketTypeDto(TicketType ticketType);

	ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

	@Mapping(target = "price", source = "ticket.ticketType.price")
	@Mapping(target = "description", source = "ticket.ticketType.description")
	@Mapping(target = "eventName", source = "ticket.ticketType.event.name")
	@Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
	@Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
	@Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
	GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);

}
