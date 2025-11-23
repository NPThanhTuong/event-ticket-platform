package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.ErrorDto;
import com.tuong.tickets.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> handleUserNotFound(UserNotFoundException e) {
		log.error("Caught UserNotFoundException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("User not found");

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<ErrorDto> handleEventNotFound(EventNotFoundException e) {
		log.error("Caught EventNotFoundException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Event not found");

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TicketTypeNotFoundException.class)
	public ResponseEntity<ErrorDto> handleTicketTypeNotFoundException(TicketTypeNotFoundException e) {
		log.error("Caught TicketTypeNotFoundException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Ticket type not found");

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EventUpdateException.class)
	public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException e) {
		log.error("Caught EventUpdateException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Unable to update event");

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(QrCodeGenerationException.class)
	public ResponseEntity<ErrorDto> handleQrCodeGenerationException(QrCodeGenerationException e) {
		log.error("Caught QrCodeGenerationException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Unable to generate QR code");

		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(QrCodeNotFoundException.class)
	public ResponseEntity<ErrorDto> handleQrCodeNotFoundException(QrCodeNotFoundException e) {
		log.error("Caught QrCodeNotFoundException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("QR code not found");

		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(TicketSoldOutException.class)
	public ResponseEntity<ErrorDto> handleTicketSoldOutException(TicketSoldOutException e) {
		log.error("Caught TicketSoldOutException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Tickets are sold out for this ticket type");

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<ErrorDto> handleTicketNotFoundException(TicketNotFoundException e) {
		log.error("Caught TicketNotFoundException", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Ticket is not found");

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		log.error("Caught MethodArgumentNotValidException", e);
		ErrorDto errorDto = new ErrorDto();
		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		String errorMsg = fieldErrors.stream()
				.findFirst()
				.map(fieldError ->
						fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.orElse("Validation error occurred");

		errorDto.setError(errorMsg);

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException e) {
		log.error("Caught ConstraintViolationException", e);
		ErrorDto errorDto = new ErrorDto();
		String errorMsg = e.getConstraintViolations()
				.stream()
				.findFirst()
				.map(constraintViolation ->
						constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage())
				.orElse("Constraint violation occurred");

		errorDto.setError(errorMsg);

		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleException(Exception e) {
		log.error("Caught exception", e);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("An unknown error occurred");

		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
