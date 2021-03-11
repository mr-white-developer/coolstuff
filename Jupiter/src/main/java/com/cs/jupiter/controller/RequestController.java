package com.cs.jupiter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.utility.Authentication;

@CrossOrigin
public class RequestController {
	@Autowired
	Authentication authen;
	
	
	public RequestCredential getReqHeader(HttpServletRequest request,HttpServletResponse resp){
		return (RequestCredential) request.getAttribute("credential");
	}
}
