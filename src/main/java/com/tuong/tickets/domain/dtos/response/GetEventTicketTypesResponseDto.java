package com.tuong.tickets.domain.dtos.response;

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
public class GetEventTicketTypesResponseDto {
	UUID id;
	String name;
	String description;
	Double price;
	Integer totalAvailable;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
}
