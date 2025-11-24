package com.tuong.tickets.domain.dtos.response;

import com.tuong.tickets.domain.enums.TicketValidationEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketValidationResponseDto {
	UUID ticketId;
	TicketValidationEnum status;
}
