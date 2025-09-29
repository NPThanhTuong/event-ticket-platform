package com.tuong.tickets.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTicketTypeRequestDto {

	@NotBlank(message = "Ticket type name is required")
	String name;

	@NotNull(message = "Price is required")
	@PositiveOrZero(message = "Price must be zero or greater")
	Double price;

	String description;

	Integer totalAvailable;

}
