package com.tuong.tickets.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketType {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@Column(name = "name", nullable = false)
	String name;

	@Column(name = "price", nullable = false)
	Double price;

	@Column(name = "total_available")
	Integer totalAvailable;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	Event event;
	
	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

}
