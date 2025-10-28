package com.tuong.tickets.domain.dtos.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListPublishedEventResponseDto {
	UUID id;
	String name;
	LocalDateTime start;
	LocalDateTime end;
	String venue;
}
