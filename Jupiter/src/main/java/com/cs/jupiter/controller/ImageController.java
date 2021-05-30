package com.cs.jupiter.controller;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/image")
public class ImageController  extends RequestController{

	@Autowired
	DataSource ds;
	
	
}
