package com.cs.jupiter.utility;

import java.util.Date;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class Authentication {
	private final int expiredTimeMin = 15;
	private final long ONE_MINUTE_IN_MILLIS = 60000;
	private final String key = "Cx2Y9#tX";

	public String createToken(String id1, String id2, String id3, String id4) {
		return Jwts.builder().setSubject("MIT").claim("id1", id1).claim("id2", id2).claim("id3", id3).claim("id4", id4)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiredTimeMin * ONE_MINUTE_IN_MILLIS))
				.setId(UUID.randomUUID().toString()).signWith(SignatureAlgorithm.HS256, key).compact();

	}
}
