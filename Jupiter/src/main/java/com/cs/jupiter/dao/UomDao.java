package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.cs.jupiter.model.table.Uom;
import com.cs.jupiter.utility.CommonUtility;

@Service
public class UomDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(Uom data, Connection conn) throws SQLException {
		 String sql = "INSERT INTO public.uom(id, code, name, status, mdate, cdate)VALUES (?, ?, ?, ?, ?::date, ?::date);";
		 PreparedStatement stmt = conn.prepareStatement(sql);
		 int i = 1;
		 stmt.setLong(i++, Long.parseLong(data.getId()));
		 stmt.setString(i++, data.getCode());
		 stmt.setString(i++, data.getName());
		 stmt.setInt(i++, data.getStatus());
		 stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
		 stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
		 return stmt.executeUpdate();
		
	}
	public int update(Uom data,Connection conn) throws SQLException {
		
		String sql="UPDATE public.uom SET mdate=?::date ";
		if(!data.getCode().equals("")){
			sql += " code = '" + data.getCode() + "' ";
		}
		if(!data.getName().equals("")){
			sql += " name = '" + data.getName() + "' ";
		}
		sql += " where id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		int i=1;
		stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
		stmt.setLong(i++,  Long.parseLong(data.getId()));
		
		return stmt.executeUpdate();
		
	}
}
