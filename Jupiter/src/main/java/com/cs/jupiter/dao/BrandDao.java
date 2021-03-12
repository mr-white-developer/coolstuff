package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.utility.Utlity;

@Service
public class BrandDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(Brand data, Connection conn) throws SQLException {
		 String sql = "INSERT INTO public.brand("
		 		+ "id, code, name, status, cdate, mdate, fk_brandowner)"
		 		+ "VALUES (?, ?, ?, ?, ?::date, ?::date, ?);";
		 PreparedStatement stmt = conn.prepareStatement(sql);
		 int i = 1;
		 stmt.setLong(i++, data.getId());
		 stmt.setString(i++, data.getCode());
		 stmt.setString(i++, data.getName());
		 stmt.setInt(i++, data.getStatus());
		 stmt.setString(i++, Utlity.convertDate_2db(data.getMdate()));
		 stmt.setString(i++, Utlity.convertDate_2db(data.getCdate()));
		 stmt.setLong(i++, data.getFk_brandowner());
		 return stmt.executeUpdate();
		
	}
}
