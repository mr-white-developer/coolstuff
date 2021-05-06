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
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.service.BrandService;
import com.cs.jupiter.utility.ComEnum;

@RestController
@RequestMapping("/brand-setup")
public class BrandController extends RequestController {
	@Autowired
	DataSource ds;

	@Autowired
	BrandService bService;

	@PostMapping(value = "/get-brands")
	public ViewResult<Brand> getBrand(@RequestBody Brand cri, HttpServletRequest req, HttpServletResponse resp) {
		try (Connection con = ds.getConnection()) {
			return bService.getAll(cri, getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<Brand>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

	@PostMapping(value = "/save-brands")
	public ViewResult<Brand> saveBrand(@RequestBody Brand data, HttpServletRequest req, HttpServletResponse resp) {
		try (Connection con = ds.getConnection()) {
			return bService.save(data, getReqHeader(req, resp),con);
		} catch (SQLException e) {
			return new ViewResult<Brand>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
		
	}

	@PostMapping(value = "/update")
	public ViewResult<Brand> updateBrand(@RequestBody Brand cri, HttpServletRequest req, HttpServletResponse resp) {
		try (Connection con = ds.getConnection()) {
			return bService.updateById(cri, getReqHeader(req, resp),con);
		} catch (SQLException e) {
			return new ViewResult<Brand>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

	@PostMapping(value = "/delete")
	public ViewResult<Brand> deleteBrand(@RequestBody Brand cri, HttpServletRequest req, HttpServletResponse resp) {
		try (Connection con = ds.getConnection()) {
			return bService.deleteById(cri, getReqHeader(req, resp),con);
		} catch (SQLException e) {
			return new ViewResult<Brand>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

	@GetMapping(value = "/getbrand-byid/{id}")
	public ViewResult<Brand> getById(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse resp) {
		try (Connection con = ds.getConnection()) {
			return bService.getById(id, getReqHeader(req, resp),con);
		} catch (SQLException e) {
			return new ViewResult<Brand>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
}
