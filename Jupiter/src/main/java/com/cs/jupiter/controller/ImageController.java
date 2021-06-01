package com.cs.jupiter.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.ImageData;
import com.cs.jupiter.service.ImageService;
import com.cs.jupiter.utility.Authentication;
import com.cs.jupiter.utility.ComEnum;


@RestController
@RequestMapping("/image")
public class ImageController  extends RequestController{
	@Autowired
	Authentication authen;
	@Autowired
	ImageService service;
	@Autowired
	Environment env;
	@Autowired
	DataSource ds;
	
	@PostMapping(value = "/decrypt/{value}")
	public ViewResult<String> getImageByStock(@PathVariable("value") String v,
			HttpServletRequest req,HttpServletResponse resp){
		ViewResult<String> rtn = new ViewResult<>();
		rtn.data = authen.decrypt(v);
		rtn.status = ComEnum.ErrorStatus.Success.getCode();
		return rtn;
	}
	@GetMapping(value = "/find/{id}")
	public ViewResult<ImageData> getPathById(@PathVariable("id") String v,
			HttpServletRequest req,HttpServletResponse resp){
		try (Connection con = ds.getConnection()) {
			return service.getById(v, getReqHeader(req, resp), con);
		}catch (Exception e) {
			return new ViewResult<ImageData>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}
	@GetMapping(value = "/getpath")
	public ViewResult<String> getPath(
			HttpServletRequest req,HttpServletResponse resp){
		ViewResult<String> rtn ;
		try (Connection con = ds.getConnection()) {
			rtn =  new ViewResult<>();
			rtn.data = env.getProperty("image-path");
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		}catch (Exception e) {
			return new ViewResult<String>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
		return rtn;
	}
	
	
}
