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
import com.cs.jupiter.model.table.Company;
import com.cs.jupiter.service.CompanyService;
import com.cs.jupiter.utility.ComEnum;

@RestController
@RequestMapping("/company-setup")
public class CompanyController extends RequestController{
	@Autowired
	CompanyService service;
	@Autowired
	DataSource ds;

	@PostMapping(value = "/save")
	public ViewResult<Company> saveBrand(
			@RequestBody Company data,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return service.save(data, getReqHeader(req, resp),con);
		} catch (SQLException e) {
			return new ViewResult<Company>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/getall")
	public ViewResult<Company> getBrandOwner(@RequestBody Company cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return service.getAll(cri, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Company>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	
	@GetMapping(value = "/getbyid/{id}")
	public ViewResult<Company> getById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return service.getById(id, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Company>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/update")
	public ViewResult<Company> updateBrandOwner(@RequestBody Company cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return service.updateById(cri, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Company>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/delete")
	public ViewResult<Company> deleteBrandOwner(@RequestBody Company cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return service.deleteById(cri, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Company>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

}
