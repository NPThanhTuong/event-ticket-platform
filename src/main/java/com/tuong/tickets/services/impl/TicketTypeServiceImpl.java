package com.tuong.tickets.services.impl;

import com.google.zxing.WriterException;
import com.tuong.tickets.domain.entities.Ticket;
import com.tuong.tickets.domain.entities.TicketType;
import com.tuong.tickets.domain.entities.User;
import com.tuong.tickets.domain.enums.TicketStatusEnum;
import com.tuong.tickets.exceptions.TicketSoldOutException;
import com.tuong.tickets.exceptions.TicketTypeNotFoundException;
import com.tuong.tickets.exceptions.UserNotFoundException;
import com.tuong.tickets.repositories.TicketRepository;
import com.tuong.tickets.repositories.TicketTypeRepository;
import com.tuong.tickets.repositories.UserRepository;
import com.tuong.tickets.services.QrCodeService;
import com.tuong.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
	private final UserRepository userRepository;
	private final TicketTypeRepository ticketTypeRepository;
	private final TicketRepository ticketRepository;
	private final QrCodeService qrCodeService;

	@Override
	@Transactional
	public void purchaseTicket(UUID userId, UUID tickTypeId) throws IOException, WriterException {
		User user = userRepository.findById(userId).orElseThrow(() ->
				new UserNotFoundException(String.format("User with ID '%s' was not found", userId))
		);

		TicketType ticketType = ticketTypeRepository.findByIdWithLock(tickTypeId).orElseThrow(() ->
				new TicketTypeNotFoundException(String.format("Ticket type with ID '%s' was not found", tickTypeId))
		);

		int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
		Integer totalAvailable = ticketType.getTotalAvailable();

		if (purchasedTickets >= totalAvailable)
			throw new TicketSoldOutException("Tickets of the ticket type are sold out");

		Ticket ticket = new Ticket();
		ticket.setStatus(TicketStatusEnum.PURCHASED);
		ticket.setTicketType(ticketType);
		ticket.setPurchaser(user);
		ticketType.getTickets().add(ticket);

		Ticket savedTicket = ticketRepository.save(ticket);
		qrCodeService.generateQrCode(savedTicket);

		ticketRepository.save(savedTicket);
	}
}
