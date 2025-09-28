package com.tuong.tickets.domain.entities;

import com.tuong.tickets.domain.enums.TicketValidationEnum;
import com.tuong.tickets.domain.enums.TicketValidationMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ticket_validations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketValidation {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	TicketValidationEnum status;

	@Column(name = "validation_date_time", nullable = false)
	LocalDateTime validationDateTime;

	@Column(name = "validation_method", nullable = false)
	@Enumerated(EnumType.STRING)
	TicketValidationMethodEnum validationMethod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	Ticket ticket;

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		TicketValidation that = (TicketValidation) o;
		return Objects.equals(id, that.id) && status == that.status && Objects.equals(validationDateTime, that.validationDateTime) && validationMethod == that.validationMethod && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, status, validationDateTime, validationMethod, createdAt, updatedAt);
	}

}
