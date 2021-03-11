package com.cs.jupiter.utility;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

import com.cs.jupiter.model.data.RequestCredential;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class Authentication {
	private final int EXPIRE_TIME_INMIN = 15;
	private final long ONE_MINUTE_IN_MILLIS = 60000;
	private final String KEY = "Cx2Y9#tX";
	public final String TOKEN_NAME = "api-token";

	public String createToken(String id1, String id2, boolean id3, String id4) {
		return Jwts.builder().setSubject("COOLSTUFF_SOFT").claim("id1", id1).claim("id2", id2).claim("id3", id3)
				.claim("id4", id4).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_INMIN * ONE_MINUTE_IN_MILLIS))
				.setId(UUID.randomUUID().toString()).signWith(SignatureAlgorithm.HS256, KEY).compact();

	}

	public RequestCredential readToken(String tokenString, HttpServletResponse resp) {
		RequestCredential credential = new RequestCredential();
		try {
			
			Claims claims = (Claims) Jwts.parser().setSigningKey(KEY).parseClaimsJws(tokenString).getBody();
			credential.setUserId((String) claims.get("id1"));
			credential.setRole((String) claims.get("id2"));
			credential.setGust((boolean) claims.get("id3"));
			credential.setAccess_datetime((String) claims.get("id4"));
		} catch (Exception e) {
			System.out.println("jwt token fail");
			resp.setStatus(401);
			
		}
		return credential;
	}
}
