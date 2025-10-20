package com.tuong.tickets.services;

import com.tuong.tickets.domain.dtos.CreateEventRequest;
import com.tuong.tickets.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface EventService {
	Event createEvent(UUID organizerId, CreateEventRequest createEventRequest);

	Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);
}
