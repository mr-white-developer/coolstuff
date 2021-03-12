package com.cs.jupiter.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;

@RestController
@RequestMapping("/brand-setup")
public class BrandController {

	@GetMapping(value = "/get-brands")
	public ViewResult<RequestCredential> getBrand(){
		return null;
	}
	@PostMapping(value = "/save-brands")
	public ViewResult<RequestCredential> saveBrand(
			@RequestBody Map<String,Object> data,
			HttpServletRequest req){
		return null;
	}
}
