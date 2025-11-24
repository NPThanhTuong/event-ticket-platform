package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.request.TicketValidationRequestDto;
import com.tuong.tickets.domain.dtos.response.TicketValidationResponseDto;
import com.tuong.tickets.domain.entities.TicketValidation;
import com.tuong.tickets.domain.enums.TicketValidationMethodEnum;
import com.tuong.tickets.mappers.TicketValidationMapper;
import com.tuong.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/ticket-validations")
public class TicketValidationController {
	private final TicketValidationService ticketValidationService;
	private final TicketValidationMapper ticketValidationMapper;

	@PostMapping
	public ResponseEntity<TicketValidationResponseDto> validateTicket(
			@RequestBody TicketValidationRequestDto ticketValidationRequestDto
	) {
		TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
		TicketValidation ticketValidation;
		if (TicketValidationMethodEnum.MANUAL.equals(method))
			ticketValidation = ticketValidationService
					.validateTicketByManually(ticketValidationRequestDto.getId());
		else
			ticketValidation = ticketValidationService
					.validateTicketBHyQrCode(ticketValidationRequestDto.getId());

		return ResponseEntity.ok(
				ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
	}
}
