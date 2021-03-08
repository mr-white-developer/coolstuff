package com.cs.os.controller;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Main {

	@Autowired
	DataSource ds;

	@Autowired
	JdbcTemplate dao;

	
}
