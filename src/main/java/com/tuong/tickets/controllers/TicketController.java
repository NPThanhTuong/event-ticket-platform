package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.response.GetTicketResponseDto;
import com.tuong.tickets.domain.dtos.response.ListTicketResponseDto;
import com.tuong.tickets.domain.entities.Ticket;
import com.tuong.tickets.mappers.TicketMapper;
import com.tuong.tickets.services.QrCodeService;
import com.tuong.tickets.services.TicketService;
import com.tuong.tickets.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
	private final TicketMapper ticketMapper;
	private final TicketService ticketService;
	private final QrCodeService qrCodeService;

	@GetMapping
	public Page<ListTicketResponseDto> listTickets(
			@AuthenticationPrincipal Jwt jwt,
			Pageable pageable) {

		UUID userId = JwtUtils.parseUserId(jwt);
		Page<Ticket> tickets = ticketService.listTicketsForUser(userId, pageable);

		return tickets.map(ticketMapper::toListTicketResponseDto);
	}

	@GetMapping(path = "/{ticketId}")
	public ResponseEntity<GetTicketResponseDto> getTicket(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID ticketId
	) {
		UUID userId = JwtUtils.parseUserId(jwt);

		return ticketService.getTicketForUser(ticketId, userId)
				.map(ticketMapper::toGetTicketResponseDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(path = "/{ticketId}/qr-codes")
	public ResponseEntity<byte[]> getTicketQrCode(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable UUID ticketId
	) {
		UUID userId = JwtUtils.parseUserId(jwt);
		byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(userId, ticketId);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.IMAGE_PNG);
		httpHeaders.setContentLength(qrCodeImage.length);

		return ResponseEntity.ok()
				.headers(httpHeaders)
				.body(qrCodeImage);
	}

}
