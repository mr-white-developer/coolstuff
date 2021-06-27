package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Category;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class CategoryDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource ds;

	public ViewResult<Category> insert(Category data, Connection conn) {
		ViewResult<Category> rs = new ViewResult<>();
		try {
			String sql = "INSERT INTO public.category(id, code, name, status, cdate, mdate)VALUES (?, ?, ?, ?, ?::date, ?::date);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));

			if (stmt.executeUpdate() > 0) {
				rs.status = ComEnum.ErrorStatus.Success.getCode();
			}
		} catch (SQLException e) {
			rs.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rs.message = e.getMessage();
		}
		return rs;
	}

	public ViewResult<Category> update(Category data, Connection conn) {
		ViewResult<Category> rs = new ViewResult<>();
		try {
			String sql = "UPDATE public.category";
			PrepareQuery ps = new PrepareQuery();
			ps.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			ps.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			sql += ps.createSetStatement();
			ps = new PrepareQuery();
			ps.add("id", Long.parseLong(data.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += ps.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();

			rs.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rs.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rs.message = e.getMessage();
		}
		return rs;

	}

	public ViewResult<Category> getAll(Category cri, Connection conn) {

		ViewResult<Category> rtn = new ViewResult<>();
		try {
			String sql = "select row_number() over(order by cat.name asc) as row_num," + "count(*) over() as total,"
					+ "cat.id as cat_id," + "cat.code as cat_code," + "cat.name as cat_name,"
					+ "cat.status as cat_status," + "cat.cdate as cat_cdate," + "cat.mdate as cat_mdate"
					+ " from category cat left join subcategory sub on sub.fk_category=cat.id";
			PrepareQuery ps = new PrepareQuery();
			ps.add("cat.id", Long.parseLong(cri.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			ps.add("cat.name", cri.getName(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("cat.code", cri.getCode(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("cat.status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);

			if (cri.getSubCategories().size() == 1) {
				ps.add("sub.name", cri.getSubCategories().get(0).getName(), PrepareQuery.Operator.LIKE_ALL,
						PrepareQuery.Type.VARCHAR);
				ps.add("sub.code", cri.getSubCategories().get(0).getCode(), PrepareQuery.Operator.LIKE_ALL,
						PrepareQuery.Type.VARCHAR);
			}

			sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(),
					"cat.id,cat.code,cat.name,cat.status,cat.cdate,cat.mdate", "cat.name", "asc");
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Category> list = new ArrayList<>();
			Category b = null;
			while (rs.next()) {
				b = new Category();
				b.setRowNumber(rs.getInt("row_num"));
				if (list.size() == 0)
					rtn.totalItem = rs.getInt("total");
				b.setId(rs.getString("cat_id"));
				b.setCode(rs.getString("cat_code"));
				b.setName(rs.getString("cat_name"));
				b.setStatus(rs.getInt("cat_status"));
				b.setCdate(rs.getDate("cat_cdate"));
				b.setMdate(rs.getDate("cat_mdate"));

				list.add(b);
			}
			rtn.list = list;
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}

	public ViewResult<Category> read(String id, Connection conn) {
		ViewResult<Category> rtn = new ViewResult<>();
		try {
			String sql = "select cat.id as cat_id," + "cat.code as cat_code," + "cat.name as cat_name,"
					+ "cat.status as cat_status," + "cat.cdate as cat_cdate," + "cat.mdate as cat_mdate"
					+ " from category cat left join subcategory sub on sub.fk_category=cat.id "
					+ " where cat.id=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(id));
			ResultSet rs = stmt.executeQuery();
		
			Category b = null;
			while (rs.next()) {
				b = new Category();
				b.setId(rs.getString("cat_id"));
				b.setCode(rs.getString("cat_code"));
				b.setName(rs.getString("cat_name"));
				b.setStatus(rs.getInt("cat_status"));
				b.setCdate(rs.getDate("cat_cdate"));
				b.setMdate(rs.getDate("cat_mdate"));
				rtn.data = b;
			}
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}

	public ViewResult<Category> delete(Category data, Connection conn) throws NumberFormatException, Exception {
		ViewResult<Category> rtn = new ViewResult<>();
		try {
			String sql = "DELETE FROM public.category";
			PrepareQuery ps = new PrepareQuery();
			ps.add("id", Long.parseLong(data.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += ps.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}
}
