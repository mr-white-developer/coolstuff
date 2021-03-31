package com.cs.jupiter.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.service.BrandOwnerService;

@RestController
@RequestMapping("/brandowner-setup")
public class BrandOwnerController extends RequestController{
	
	@Autowired
	BrandOwnerService boService;

	
	@PostMapping(value = "/save-bo")
	public ViewResult<BrandOwner> saveBrand(
			@RequestBody BrandOwner data,
			HttpServletRequest req,HttpServletResponse resp){
		return boService.save(data, getReqHeader(req, resp));
	}
}
