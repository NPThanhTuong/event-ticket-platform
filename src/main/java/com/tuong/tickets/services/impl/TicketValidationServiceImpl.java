package com.tuong.tickets.services.impl;

import com.tuong.tickets.domain.entities.QrCode;
import com.tuong.tickets.domain.entities.Ticket;
import com.tuong.tickets.domain.entities.TicketValidation;
import com.tuong.tickets.domain.enums.QrCodeStatusEnum;
import com.tuong.tickets.domain.enums.TicketValidationEnum;
import com.tuong.tickets.domain.enums.TicketValidationMethodEnum;
import com.tuong.tickets.exceptions.QrCodeNotFoundException;
import com.tuong.tickets.exceptions.TicketNotFoundException;
import com.tuong.tickets.repositories.QrCodeRepository;
import com.tuong.tickets.repositories.TicketRepository;
import com.tuong.tickets.repositories.TicketValidationRepository;
import com.tuong.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {
	private final TicketValidationRepository ticketValidationRepository;
	private final QrCodeRepository qrCodeRepository;
	private final TicketRepository ticketRepository;

	@Override
	public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
		QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
				.orElseThrow(() -> new QrCodeNotFoundException(
						String.format("QR code with ID %s was not found", qrCodeId))
				);

		Ticket ticket = qrCode.getTicket();

		return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);
	}

	@Override
	public TicketValidation validateTicketByManually(UUID ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(TicketNotFoundException::new);

		return validateTicket(ticket, TicketValidationMethodEnum.MANUAL);
	}

	private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum validationMethod) {
		TicketValidation ticketValidation = new TicketValidation();
		ticketValidation.setTicket(ticket);
		ticketValidation.setValidationMethod(validationMethod);
		ticketValidation.setValidationDateTime(LocalDateTime.now());

		TicketValidationEnum ticketValidationEnum = ticket.getValidations().stream()
				.filter(v -> TicketValidationEnum.VALID.equals(v.getStatus()))
				.findFirst()
				.map(v -> TicketValidationEnum.INVALID)
				.orElse(TicketValidationEnum.VALID);

		ticketValidation.setStatus(ticketValidationEnum);

		return ticketValidationRepository.save(ticketValidation);
	}
}
