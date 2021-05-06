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
import com.cs.jupiter.model.table.Category;
import com.cs.jupiter.service.CategoryService;
import com.cs.jupiter.utility.ComEnum;

@RestController
@RequestMapping("/category-setup")
public class CategoryController extends RequestController{
	@Autowired
	DataSource ds;
	
	@Autowired
	CategoryService catService;

	
	@PostMapping(value = "/save")
	public ViewResult<Category> saveBrand(
			@RequestBody Category data,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return catService.save(data, getReqHeader(req, resp),con);
		} catch (SQLException e) {
			return new ViewResult<Category>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/getall")
	public ViewResult<Category> getCategory(@RequestBody Category cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return catService.getAll(cri, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Category>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	
	@GetMapping(value = "/getbyid/{id}")
	public ViewResult<Category> getById(@PathVariable("id") String id,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return catService.getById(id, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Category>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/update")
	public ViewResult<Category> updateCategory(@RequestBody Category cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return catService.updateById(cri, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Category>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@PostMapping(value = "/delete")
	public ViewResult<Category> deleteCategory(@RequestBody Category cri,HttpServletRequest req, HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return catService.deleteById(cri, getReqHeader(req,resp),con);
		} catch (SQLException e) {
			return new ViewResult<Category>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

}
