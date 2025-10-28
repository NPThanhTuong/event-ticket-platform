package com.tuong.tickets.services.impl;

import com.tuong.tickets.domain.dtos.CreateEventRequest;
import com.tuong.tickets.domain.dtos.UpdateEventRequest;
import com.tuong.tickets.domain.dtos.UpdateTicketTypeRequest;
import com.tuong.tickets.domain.entities.Event;
import com.tuong.tickets.domain.entities.TicketType;
import com.tuong.tickets.domain.entities.User;
import com.tuong.tickets.domain.enums.EventStatusEnum;
import com.tuong.tickets.exceptions.EventNotFoundException;
import com.tuong.tickets.exceptions.EventUpdateException;
import com.tuong.tickets.exceptions.TicketTypeNotFoundException;
import com.tuong.tickets.exceptions.UserNotFoundException;
import com.tuong.tickets.repositories.EventRepository;
import com.tuong.tickets.repositories.UserRepository;
import com.tuong.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;

	@Override
	@Transactional
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

	@Override
	public Optional<Event> getEventForOrganizerId(UUID organizerId, UUID id) {
		return eventRepository.findByIdAndOrganizerId(id, organizerId);
	}

	@Override
	@Transactional
	public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event) {
		if (event.getId() == null)
			throw new EventUpdateException("Event ID cannot be null");

		if (!eventId.equals(event.getId()))
			throw new EventNotFoundException("Cannot update the ID of an event");

		Event existingEvent = eventRepository
				.findByIdAndOrganizerId(eventId, organizerId)
				.orElseThrow(() -> new EventNotFoundException(
						String.format("Event with ID '%s' does not exist", eventId))
				);

		existingEvent.setName(event.getName());
		existingEvent.setStart(event.getStart());
		existingEvent.setEnd(event.getEnd());
		existingEvent.setVenue(event.getVenue());
		existingEvent.setSalesStart(event.getSalesStart());
		existingEvent.setSalesEnd(event.getSalesEnd());
		existingEvent.setStatus(event.getStatus());

		Set<UUID> requestTicketTypeIds = event.getTicketTypes()
				.stream()
				.map(UpdateTicketTypeRequest::getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		existingEvent.getTicketTypes().removeIf(existingTicketType ->
				!requestTicketTypeIds.contains(existingTicketType.getId()));

		Map<UUID, TicketType> existingTicketTypeIndex = existingEvent.getTicketTypes()
				.stream()
				.collect(Collectors.toMap(TicketType::getId, Function.identity()));

		for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
			if (ticketType.getId() == null) {
//				Create
				TicketType ticketTypeToCreate = new TicketType();
				ticketTypeToCreate.setName(ticketType.getName());
				ticketTypeToCreate.setDescription(ticketType.getDescription());
				ticketTypeToCreate.setPrice(ticketType.getPrice());
				ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());

				ticketTypeToCreate.setEvent(existingEvent);
				existingEvent.getTicketTypes().add(ticketTypeToCreate);
			} else if (existingTicketTypeIndex.containsKey(ticketType.getId())) {
//				Update
				TicketType existingTicketType = existingTicketTypeIndex.get(ticketType.getId());
				existingTicketType.setName(ticketType.getName());
				existingTicketType.setDescription(ticketType.getDescription());
				existingTicketType.setPrice(ticketType.getPrice());
				existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
			} else {
				throw new TicketTypeNotFoundException(
						String.format("Ticket type with ID '%s' does not exist", ticketType.getId())
				);
			}
		}

		return eventRepository.save(existingEvent);
	}

	@Override
	public void deleteEventForOrganizer(UUID organizerId, UUID id) {
		getEventForOrganizerId(organizerId, id).ifPresent(eventRepository::delete);
	}

	@Override
	public Page<Event> listPublishedEvents(Pageable pageable) {
		return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
	}

	@Override
	public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
		return eventRepository.searchEvents(query, pageable);
	}

	@Override
	public Optional<Event> getPublishedEvent(UUID id) {
		return eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
	}

}
