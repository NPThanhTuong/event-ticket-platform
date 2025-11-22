package com.tuong.tickets.controllers;

import com.google.zxing.WriterException;
import com.tuong.tickets.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

import static com.tuong.tickets.utils.JwtUtils.parseUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {
	private final TicketTypeService ticketTypeService;

	@PostMapping(path = "/{ticketTypeId}/tickets")
	public ResponseEntity<Void> purchaseTicket(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID ticketTypeId
	) throws IOException, WriterException {
		UUID userId = parseUserId(jwt);
		ticketTypeService.purchaseTicket(userId, ticketTypeId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
