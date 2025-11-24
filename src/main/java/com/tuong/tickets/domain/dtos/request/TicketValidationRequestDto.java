package com.tuong.tickets.domain.dtos.request;

import com.tuong.tickets.domain.enums.TicketValidationMethodEnum;
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
public class TicketValidationRequestDto {
	UUID id;
	TicketValidationMethodEnum method;
}
