package com.tuong.tickets.services;

import com.tuong.tickets.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
	TicketValidation validateTicketByQrCode(UUID qrCodeId);

	TicketValidation validateTicketByManually(UUID ticketId);
}
