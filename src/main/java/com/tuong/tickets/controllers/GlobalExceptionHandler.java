package com.tuong.tickets.controllers;

import com.tuong.tickets.domain.dtos.ErrorDto;
import com.tuong.tickets.exceptions.UserNotFoundException;
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
