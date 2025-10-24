package com.tuong.tickets.domain.dtos;

import com.tuong.tickets.domain.enums.EventStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventRequest {
	UUID id;
	String name;
	LocalDateTime start;
	LocalDateTime end;
	String venue;
	LocalDateTime salesStart;
	LocalDateTime salesEnd;
	EventStatusEnum status;
	List<UpdateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
