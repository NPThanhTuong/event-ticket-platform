package com.tuong.tickets.services.impl;

import com.tuong.tickets.domain.dtos.CreateEventRequest;
import com.tuong.tickets.domain.entities.Event;
import com.tuong.tickets.domain.entities.TicketType;
import com.tuong.tickets.domain.entities.User;
import com.tuong.tickets.exceptions.UserNotFoundException;
import com.tuong.tickets.repositories.EventRepository;
import com.tuong.tickets.repositories.UserRepository;
import com.tuong.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;

	@Override
	public Event createEvent(UUID organizerId, CreateEventRequest eventRequest) {
		User organizer = userRepository
				.findById(organizerId)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("User with ID '%s' not found", organizerId)));

		Event eventToCreate = new Event();
		eventToCreate.setName(eventRequest.getName());
		eventToCreate.setStart(eventRequest.getStart());
		eventToCreate.setEnd(eventRequest.getEnd());
		eventToCreate.setVenue(eventRequest.getVenue());
		eventToCreate.setSalesStart(eventRequest.getSalesStart());
		eventToCreate.setSalesEnd(eventRequest.getSalesEnd());
		eventToCreate.setStatus(eventRequest.getStatus());
		eventToCreate.setOrganizer(organizer);

		List<TicketType> ticketTypesToCreate = eventRequest.getTicketTypes()
				.stream().map(ticketTypeRequest -> {
					TicketType ticketTypeToCreate = new TicketType();
					ticketTypeToCreate.setName(ticketTypeRequest.getName());
					ticketTypeToCreate.setDescription(ticketTypeRequest.getDescription());
					ticketTypeToCreate.setPrice(ticketTypeRequest.getPrice());
					ticketTypeToCreate.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
					ticketTypeToCreate.setEvent(eventToCreate);

					return ticketTypeToCreate;
				}).toList();

		eventToCreate.setTicketTypes(ticketTypesToCreate);

		return eventRepository.save(eventToCreate);
	}

	@Override
	public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
		return eventRepository.findByOrganizerId(organizerId, pageable);
	}

//	@Override
//	public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
//		return Optional.empty();
//	}

//	@Override
//	public List<Event> getAllEvents() {
//		return eventRepository.findAll();
//	}

}
