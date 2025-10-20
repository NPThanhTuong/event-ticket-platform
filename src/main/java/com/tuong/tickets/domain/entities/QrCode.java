package com.tuong.tickets.domain.entities;

import com.tuong.tickets.domain.enums.QrCodeStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QrCode {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	QrCodeStatusEnum status;

	@Column(name = "value", nullable = false)
	String value;

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
		QrCode qrCode = (QrCode) o;
		return Objects.equals(id, qrCode.id) && status == qrCode.status && Objects.equals(value, qrCode.value) && Objects.equals(createdAt, qrCode.createdAt) && Objects.equals(updatedAt, qrCode.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, status, value, createdAt, updatedAt);
	}

}
