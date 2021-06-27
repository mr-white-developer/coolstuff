package com.cs.jupiter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.utility.Authentication;
import com.cs.jupiter.utility.KeyFactory;

@RestController
@RequestMapping("/main")

public class Main extends RequestController{
	@Autowired
	Authentication authen;

	@GetMapping(value = "/get-token")
	public ViewResult<RequestCredential> getStocks(HttpServletRequest req,HttpServletResponse resp){
		return new ViewResult<RequestCredential>() ;
	}
	@GetMapping(value = "/check-token")
	public ViewResult<RequestCredential> checkToken(
			HttpServletRequest req,HttpServletResponse resp){
		return new ViewResult<RequestCredential>() ;
	}



	
}
