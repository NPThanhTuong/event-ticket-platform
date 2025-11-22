package com.tuong.tickets.utils;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public final class JwtUtils {
	public JwtUtils() {
	}

	public static UUID parseUserId(Jwt jwt) {
		return UUID.fromString(jwt.getSubject());
	}
}
