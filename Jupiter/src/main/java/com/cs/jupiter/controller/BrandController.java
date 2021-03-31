package com.cs.jupiter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cs.jupiter.model.data.ViewCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.service.BrandService;

@RestController
@RequestMapping("/brand-setup")
public class BrandController extends RequestController {
	
	@Autowired
	BrandService bService;

	@PostMapping(value = "/get-brands")
	public ViewResult<Brand> getBrand(@RequestBody ViewCredential cri,HttpServletRequest req, HttpServletResponse resp){
		return bService.getAll(cri, getReqHeader(req,resp));
	}
	@PostMapping(value = "/save-brands")
	public ViewResult<Brand> saveBrand(
			@RequestBody Brand data,
			HttpServletRequest req, HttpServletResponse resp){
		return bService.save(data, getReqHeader(req,resp));
	}
}
