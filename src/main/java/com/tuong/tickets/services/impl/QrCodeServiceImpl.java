package com.tuong.tickets.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tuong.tickets.domain.entities.QrCode;
import com.tuong.tickets.domain.entities.Ticket;
import com.tuong.tickets.domain.enums.QrCodeStatusEnum;
import com.tuong.tickets.exceptions.QrCodeGenerationException;
import com.tuong.tickets.exceptions.QrCodeNotFoundException;
import com.tuong.tickets.repositories.QrCodeRepository;
import com.tuong.tickets.services.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {
	private static final int QR_HEIGHT = 300;
	private static final int QR_WIDTH = 300;

	private final QrCodeRepository qrCodeRepository;
	private final QRCodeWriter qrCodeWriter;


	@Override
	public void generateQrCode(Ticket ticket) {
		UUID uniqueId = UUID.randomUUID();

		try {
			String qrCodeImage = generateQrCodeImage(uniqueId);
			QrCode qrCode = new QrCode();
			qrCode.setId(uniqueId);
			qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
			qrCode.setValue(qrCodeImage);
			qrCode.setTicket(ticket);

			qrCodeRepository.saveAndFlush(qrCode);
		} catch (WriterException | IOException e) {
			throw new QrCodeGenerationException("Failed to generate QR Code", e);
		}
	}

	@Override
	public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
		QrCode qrCode = qrCodeRepository
				.findByTicketIdAndTicketPurchaserId(ticketId, userId)
				.orElseThrow(QrCodeNotFoundException::new);
		try {
			return Base64.getDecoder().decode(qrCode.getValue());
		} catch (IllegalArgumentException ex) {
			log.error("Invalid base64 QR code for ticket ID: {}", ticketId, ex);
			throw new QrCodeNotFoundException();
		}
	}

	private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
		BitMatrix bitMatrix = qrCodeWriter.encode(
				uniqueId.toString(),
				BarcodeFormat.QR_CODE,
				QR_WIDTH,
				QR_HEIGHT);

		BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(bufferedImage, "PNG", baos);
			byte[] imageBytes = baos.toByteArray();

			return Base64.getEncoder().encodeToString(imageBytes);
		}
	}
}
