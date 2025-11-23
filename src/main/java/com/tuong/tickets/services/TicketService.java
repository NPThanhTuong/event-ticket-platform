package com.tuong.tickets.services;

import com.tuong.tickets.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {
	Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable);

	Optional<Ticket> getTicketForUser(UUID ticketId, UUID userId);
	
}
