package com.tuong.tickets.services;

import com.google.zxing.WriterException;
import com.tuong.tickets.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface QrCodeService {
	void generateQrCode(Ticket ticket) throws WriterException, IOException;

	byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
	
}
