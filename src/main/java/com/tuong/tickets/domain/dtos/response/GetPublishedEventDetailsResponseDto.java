package com.tuong.tickets.domain.dtos.response;

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
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetPublishedEventDetailsResponseDto {
	UUID id;
	String name;
	LocalDateTime start;
	LocalDateTime end;
	String venue;
	EventStatusEnum status;
	List<GetPublishedEventTicketTypesResponseDto> ticketTypes = new ArrayList<>();
}
