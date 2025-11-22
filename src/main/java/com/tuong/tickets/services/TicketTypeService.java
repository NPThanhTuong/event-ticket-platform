package com.tuong.tickets.services;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public interface TicketTypeService {
	void purchaseTicket(UUID userId, UUID tickTypeId) throws IOException, WriterException;
}
