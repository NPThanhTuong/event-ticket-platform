package com.tuong.tickets.domain.dtos.response;

import com.tuong.tickets.domain.enums.TicketStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTicketResponseDto {
	UUID id;
	TicketStatusEnum status;
	ListTicketResponseTicketTypeDto ticketType;
	
}
