package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.CreateEventRequest;
import com.tuong.tickets.domain.dtos.UpdateEventRequest;
import com.tuong.tickets.domain.dtos.request.CreateEventRequestDto;
import com.tuong.tickets.domain.dtos.request.UpdateEventRequestDto;
import com.tuong.tickets.domain.dtos.response.CreateEventResponseDto;
import com.tuong.tickets.domain.dtos.response.GetEventDetailsResponseDto;
import com.tuong.tickets.domain.dtos.response.ListEventResponseDto;
import com.tuong.tickets.domain.dtos.response.UpdateEventResponseDto;
import com.tuong.tickets.domain.entities.Event;
import com.tuong.tickets.exceptions.EventNotFoundException;
import com.tuong.tickets.mappers.EventMapper;
import com.tuong.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
@Slf4j
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
		UUID userId = parseUserId(jwt);
		Event event = eventService.createEvent(userId, createEventRequest);
		CreateEventResponseDto createEventResponseDto = eventMapper.toDto(event);

		return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<ListEventResponseDto>> getAllEvents(
			@AuthenticationPrincipal Jwt jwt,
			Pageable pageable
	) {
		UUID organizerId = parseUserId(jwt);
		Page<Event> events = eventService.listEventsForOrganizer(organizerId, pageable);

		return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDto));
	}

	@GetMapping(path = "/{eventId}")
	public ResponseEntity<GetEventDetailsResponseDto> getEventForOrganizer(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID eventId
	) {
		UUID organizerId = parseUserId(jwt);

		return eventService.getEventForOrganizerId(organizerId, eventId)
				.map(eventMapper::toGetEventDetailsResponseDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<UpdateEventResponseDto> fullUpdateEvent(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID eventId,
			@Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
	) {
		UUID organizerId = parseUserId(jwt);
		UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
		Event updatedEvent = eventService.updateEventForOrganizer(organizerId, eventId, updateEventRequest);
		UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(updatedEvent);

		return ResponseEntity.ok(updateEventResponseDto);
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<Void> deleteEventForOrganizer(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID eventId
	) {
		UUID organizerId = parseUserId(jwt);
		eventService.deleteEventForOrganizer(organizerId, eventId);

		return ResponseEntity.noContent().build();
	}

	private UUID parseUserId(Jwt jwt) {
		log.info(jwt.getClaims().toString());
		
		return UUID.fromString(jwt.getSubject());
	}
}
