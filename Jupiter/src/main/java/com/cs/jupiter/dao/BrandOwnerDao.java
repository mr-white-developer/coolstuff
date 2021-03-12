package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.utility.Utlity;

@Service
public class BrandOwnerDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(BrandOwner data, Connection conn) throws SQLException {
		 String sql = "INSERT INTO public.brandowner("
		 + "id, code, name, status, mdate, cdate) VALUES (?, ?, ?, ?, ?::date, ?::date);";
		 PreparedStatement stmt = conn.prepareStatement(sql);
		 int i = 1;
		 stmt.setLong(i++, data.getId());
		 stmt.setString(i++, data.getCode());
		 stmt.setString(i++, data.getName());
		 stmt.setInt(i++, data.getStatus());
		 stmt.setString(i++, Utlity.convertDate_2db(data.getMdate()));
		 stmt.setString(i++, Utlity.convertDate_2db(data.getCdate()));
		 return stmt.executeUpdate();
		
	}
}
