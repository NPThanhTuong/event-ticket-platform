package com.tuong.tickets.domain.dtos.response;

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
public class ListEventTicketTypeResponseDto {
	UUID id;
	String name;
	String description;
	Double price;
	Integer totalAvailable;
}
