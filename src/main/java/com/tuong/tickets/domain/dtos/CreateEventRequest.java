package com.tuong.tickets.domain.dtos;

import com.tuong.tickets.domain.enums.EventStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEventRequest {

	String name;
	LocalDateTime start;
	LocalDateTime end;
	String venue;
	LocalDateTime salesStart;
	LocalDateTime salesEnd;
	EventStatusEnum status;
	List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();

}
