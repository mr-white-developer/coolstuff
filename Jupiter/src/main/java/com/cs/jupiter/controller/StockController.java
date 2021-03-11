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
@RequestMapping("/stock-setup")
public class StockController extends RequestController{
	
	@GetMapping(value = "/get-stocks")
	public ViewResult<RequestCredential> getStocks(){
		System.out.println("getstock");
		return "GetStock";
	}
	@PostMapping(value = "/save-stocks")
	public ViewResult<RequestCredential> saveStocks(
			@RequestBody Map<String,Object> data,
			HttpServletRequest req){
		return new ViewResult<RequestCredential>() ;
	}
}
