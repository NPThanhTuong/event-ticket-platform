package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.CreateEventRequest;
import com.tuong.tickets.domain.dtos.request.CreateEventRequestDto;
import com.tuong.tickets.domain.dtos.response.CreateEventResponseDto;
import com.tuong.tickets.domain.entities.Event;
import com.tuong.tickets.mappers.EventMapper;
import com.tuong.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(params = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {
	private final EventMapper eventMapper;
	private final EventService eventService;

	@PostMapping
	public ResponseEntity<CreateEventResponseDto> createEvent(
			@AuthenticationPrincipal Jwt jwt,
			@Valid
			@RequestBody
			CreateEventRequestDto createEventRequestDto
	) {
		CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
		UUID userId = UUID.fromString(jwt.getSubject());
		Event event = eventService.createEvent(userId, createEventRequest);
		CreateEventResponseDto createEventResponseDto = eventMapper.toDto(event);

		return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
	}
}
