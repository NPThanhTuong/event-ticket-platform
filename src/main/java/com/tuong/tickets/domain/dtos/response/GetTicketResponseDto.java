package com.tuong.tickets.domain.dtos.response;

import com.tuong.tickets.domain.enums.TicketStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTicketResponseDto {
	UUID id;
	TicketStatusEnum status;
	Double price;
	String description;
	String eventName;
	String eventVenue;
	LocalDateTime eventStart;
	LocalDateTime eventEnd;
	

}
