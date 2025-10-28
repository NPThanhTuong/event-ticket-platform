package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.response.ListPublishedEventResponseDto;
import com.tuong.tickets.domain.entities.Event;
import com.tuong.tickets.mappers.EventMapper;
import com.tuong.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

	private final EventMapper eventMapper;
	private final EventService eventService;

	@GetMapping
	public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
			@RequestParam(required = false) String q,
			Pageable pageable) {
		Page<Event> events;

		if (q != null && !q.trim().isEmpty()) {
			log.info("Query: " + (q == null) + "; " + (q.trim().isEmpty()));
			events = eventService.searchPublishedEvents(q, pageable);
		} else {
			log.info("List all");
			events = eventService.listPublishedEvents(pageable);
		}

		return ResponseEntity.ok(
				events.map(eventMapper::toListPublishedEventResponseDto)
		);
	}

}
