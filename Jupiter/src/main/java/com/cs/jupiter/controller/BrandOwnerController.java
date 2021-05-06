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
import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.service.BrandOwnerService;
import com.cs.jupiter.utility.ComEnum;

@RestController
@RequestMapping("/brandowner-setup")
public class BrandOwnerController extends RequestController{
	@Autowired
	DataSource ds;
	
	@Autowired
	BrandOwnerService boService;

	
	@PostMapping(value = "/save-bo")
	public ViewResult<BrandOwner> saveBrand(
			@RequestBody BrandOwner data,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return boService.save(data, getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<BrandOwner>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/get-brandowners")
	public ViewResult<BrandOwner> getBrandOwner(@RequestBody BrandOwner cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return boService.getAll(cri, getReqHeader(req,resp), con);
		} catch (SQLException e) {
			return new ViewResult<BrandOwner>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	
	@GetMapping(value = "/getbrand-byid/{id}")
	public ViewResult<BrandOwner> getById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return boService.getById(id, getReqHeader(req,resp), con);
		} catch (SQLException e) {
			return new ViewResult<BrandOwner>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/update")
	public ViewResult<BrandOwner> updateBrandOwner(@RequestBody BrandOwner cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return boService.updateById(cri, getReqHeader(req,resp), con);
		} catch (SQLException e) {
			return new ViewResult<BrandOwner>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/delete")
	public ViewResult<BrandOwner> deleteBrandOwner(@RequestBody BrandOwner cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return boService.deleteById(cri, getReqHeader(req,resp), con);
		} catch (SQLException e) {
			return new ViewResult<BrandOwner>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

}
