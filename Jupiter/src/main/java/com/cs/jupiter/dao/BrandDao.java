package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cs.jupiter.model.data.ViewCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.utility.CommonUtility;

@Service
public class BrandDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(Brand data, Connection conn) throws SQLException {
		String sql = "INSERT INTO public.brand(" + "id, code, name, status, cdate, mdate, fk_brandowner)"
				+ "VALUES (?, ?, ?, ?, ?::date, ?::date, ?);";
		PreparedStatement stmt = conn.prepareStatement(sql);
		int i = 1;
		stmt.setLong(i++, Long.parseLong(data.getId()));
		stmt.setString(i++, data.getCode());
		stmt.setString(i++, data.getName());
		stmt.setInt(i++, data.getStatus());
		stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
		stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
		stmt.setLong(i++, Long.parseLong(data.getFk_brandowner()));
		return stmt.executeUpdate();

	}

	public int update(Brand data, Connection conn) throws SQLException {

		String sql = "UPDATE public.brand SET mdate=?::date ";
		if (!data.getCode().equals("")) {
			sql += " code = '" + data.getCode() + "' ";
		}
		if (!data.getName().equals("")) {
			sql += " name = '" + data.getName() + "' ";
		}
		if (data.getFk_brandowner() != "-1") {
			sql += " fk_brandowner = " + data.getFk_brandowner();
		}
		sql += " where id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		int i = 1;
		stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
		stmt.setLong(i++, Long.parseLong(data.getId()));

		return stmt.executeUpdate();

	}

	public ViewResult<Brand> getAll(ViewCredential cri, Connection conn) throws SQLException {
		String sql = "select row_number() over(order by b.id desc) as row_num,"
				+ "count(*) over() as total,"
				+ "b.id as b_id,"
				+ "b.code as b_code,"
				+ "b.name as b_name,"
				+ "b.status as b_status,"
				+ "b.cdate as b_cdate,"
				+ "b.mdate as b_mdate,"
				+ "bo.code as bo_code,"
				+ "bo.name as bo_name,"
				+ "bo.status as bo_status"
				+ " from brand b inner join brandowner bo on bo.id = b.fk_brandowner where b.status=? and bo.status=?";
		if(!cri.getN1().equals("-1")){
			sql += " and b.id = " + cri.getN1();
		}
		if(!cri.getN2().equals("-1")){
			sql += " and b.fk_brandowner = " + cri.getN2();
		}
		if(cri.getCurrentRow() != -1 && cri.getMaxRowsPerPage() != -1){
			sql += CommonUtility.getPegination(cri.getCurrentRow(), cri.getMaxRowsPerPage());
		}
		PreparedStatement stmt = conn.prepareStatement(sql);
		int i = 1;
		stmt.setInt(i++, 1);
		stmt.setInt(i++, 1);
		
		ResultSet rs = stmt.executeQuery();
		ViewResult<Brand> rtn = new ViewResult<>();
		List<Brand> list = new ArrayList<>();
		Brand b = null;
		while (rs.next()){
			b = new Brand();
			b.setRowNumber(rs.getInt("row_num"));
			if(list.size()==0) rtn.totalItem = rs.getInt("total");
			b.setId(Integer.toString(rs.getString("b_id").hashCode()));
			b.setCode(rs.getString("b_code"));
			b.setName(rs.getString("b_name"));
			b.setStatus(rs.getInt("b_status"));
			b.setCdate(rs.getDate("b_cdate"));
			b.setMdate(rs.getDate("b_mdate"));
			
			b.getBrandOwner().setCode(rs.getString("bo_code"));
			b.getBrandOwner().setName(rs.getString("bo_name"));
			b.getBrandOwner().setStatus(rs.getInt("bo_status"));
			
			list.add(b);
		}
		rtn.list = list;
		return rtn;
	}
}
