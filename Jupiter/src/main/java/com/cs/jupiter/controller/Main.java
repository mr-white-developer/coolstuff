package com.cs.jupiter.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.utility.Authentication;
import com.cs.jupiter.utility.Utlity;

@RestController
@RequestMapping("/main")

public class Main extends RequestController{
	@Autowired
	Authentication authen;

	@GetMapping(value = "/get-token")
	public ViewResult<RequestCredential> getStocks(HttpServletRequest req,HttpServletResponse resp){
		return new ViewResult<RequestCredential>(null,getReqHeader(req,resp),"ok",200) ;
	}
	@GetMapping(value = "/check-token")
	public ViewResult<RequestCredential> checkToken(
			HttpServletRequest req,HttpServletResponse resp){
		return new ViewResult<RequestCredential>(null,getReqHeader(req,resp),"ok",200) ;
	}

	
}
