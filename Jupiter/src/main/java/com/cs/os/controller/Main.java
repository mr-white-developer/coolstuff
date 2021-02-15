package com.cs.os.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.os.model.data.MyUser;

@RestController
public class Main {

	@Autowired
	DataSource ds;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping("/")
	public String index() {
		try {
			DataSource ds = jdbcTemplate.getDataSource();

			Connection conn = ds.getConnection();
			if(conn!=null){
				String sql = "select id from myuser";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs  = stmt.executeQuery();
				while(rs.next()){
					return rs.getString("id");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
