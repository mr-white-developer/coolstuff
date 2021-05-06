package com.cs.jupiter.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Stock;
import com.cs.jupiter.service.StockService;
import com.cs.jupiter.utility.ComEnum;

@RestController
@RequestMapping("/stock-setup")
public class StockController extends RequestController{
	@Autowired
	DataSource ds;
	
	@Autowired
	StockService stkService;
	
	@PostMapping(value = "/getall")
	public ViewResult<Stock> getStocks(@RequestBody Stock data,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return stkService.getAll(data,getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<Stock>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@GetMapping(value = "/getbyid/{id}")
	public ViewResult<Stock> getStocks(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return stkService.getById(id,getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<Stock>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/save")
	public ViewResult<Stock> saveStocks(
			@RequestBody Stock data,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return stkService.saveStockSetup(data, getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<Stock>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/update")
	public ViewResult<Stock> updateStocks(
			@RequestBody Stock data,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return stkService.saveStockSetup(data, getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<Stock>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
}
