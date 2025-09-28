package com.tuong.tickets.domain.entities;

import com.tuong.tickets.domain.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	TicketStatusEnum status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_type_id")
	TicketType ticketType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaser_id")
	User purchaser;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	List<TicketValidation> validations = new ArrayList<>();

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	List<QrCode> qrCodes = new ArrayList<>();

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

}
