package com.tuong.tickets.domain.entities;

import com.tuong.tickets.domain.enums.EventStatusEnum;
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
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@Column(name = "name", nullable = false)
	String name;

	@Column(name = "start")
	LocalDateTime start;

	@Column(name = "end")
	LocalDateTime end;

	@Column(name = "venue", nullable = false)
	String venue;

	@Column(name = "sales_start")
	LocalDateTime salesStart;

	@Column(name = "sales_end")
	LocalDateTime salesEnd;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	EventStatusEnum status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organizer_id")
	User organizer;

	@ManyToMany(mappedBy = "attendingEvents")
	List<User> attendees = new ArrayList<>();

	@ManyToMany(mappedBy = "staffingEvents")
	List<User> staff = new ArrayList<>();

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	List<TicketType> ticketTypes = new ArrayList<>();

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

}
