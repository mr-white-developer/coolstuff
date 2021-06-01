package com.cs.jupiter.utility;

import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
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
	
	private static String KEY128 = "3867!@#$78!@#$76"; // 128 bit key
    private static String INIT_VECTOR = "RandomInitVector"; // 16 bytes IV

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
	
	public String encrypt(String value) {
		if(value == null || "".equals(value)) return "";
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY128.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String decrypt(String value) {
    	if(value == null || "".equals(value)) return "";
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY128.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(value));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    public static void main(String[] args){
    	//System.out.println(encrypt(""));
    }
   
}
