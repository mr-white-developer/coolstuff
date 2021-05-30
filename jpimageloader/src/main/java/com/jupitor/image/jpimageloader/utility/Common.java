package com.jupitor.image.jpimageloader.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Common {
	public static String httpPost(MultiValueMap<String, String> headers, Object params, String url) {
		// request(httpEntity)
		HttpEntity<?> httpEntity = new HttpEntity<>(params, headers);
		// rest template
		RestTemplate restTemplate = new RestTemplate();
		// response
		ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
		return response.getBody();
	}

	public static String httpGet(String url, String codex) {
		// request(httpEntity)
		HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders(codex));
		// rest template
		RestTemplate restTemplate = new RestTemplate();
		// response
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
		return response.getBody();

	}
	public static MultiValueMap<String, String> getHeaders(String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		Map<String, String> map = new HashMap<>();
		map.put("Accept", "application/json");
		map.put("Content-Type", "application/json");
		map.put("api-token", token);
		headers.setAll(map);
		return headers;
	}
}
