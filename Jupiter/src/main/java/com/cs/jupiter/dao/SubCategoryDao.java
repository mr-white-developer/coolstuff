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
import com.cs.jupiter.model.table.SubCategory;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class SubCategoryDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource ds;

	public ViewResult<SubCategory> insert(SubCategory data, Connection conn) throws SQLException {
		ViewResult<SubCategory> rtn = new ViewResult<>();
		try {
			String sql = "INSERT INTO public.subcategory(id, code, name, status, cdate, mdate,fk_category)VALUES (?, ?, ?, ?, ?::date, ?::date,?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setLong(i++, Long.parseLong(data.getCategory().getId()));
			if (stmt.executeUpdate() > 0)
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
			else
				rtn.status = ComEnum.ErrorStatus.ClientError.getCode();
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;

	}

	public ViewResult<SubCategory> update(SubCategory data, Connection conn) throws Exception {
		ViewResult<SubCategory> rtn = new ViewResult<>();
		try {

			String sql = "UPDATE public.subcategory";
			PrepareQuery ps = new PrepareQuery();
			ps.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			ps.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			if (data.getCategory() != null)
				ps.add("fk_category", Long.parseLong(data.getCategory().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			sql += ps.createSetStatement();
			ps = new PrepareQuery();
			ps.add("id", Long.parseLong(data.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += ps.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;

	}

	public ViewResult<SubCategory> getAll(SubCategory cri, Connection conn) throws NumberFormatException, Exception {
		ViewResult<SubCategory> rtn = new ViewResult<>();
		try {

			String sql = "select row_number() over(order by sub.name asc) as row_num," + "count(*) over() as total,"
					+ "sub.id as sub_id," + "sub.code as sub_code," + "sub.name as sub_name,"
					+ "sub.status as sub_status," + "sub.cdate as sub_cdate," + "sub.mdate as sub_mdate,"
					+ "cat.id as cat_id," + "cat.name as cat_name"
					+ " from subcategory sub inner join category cat on cat.id = sub.fk_category";
			PrepareQuery ps = new PrepareQuery();

			ps.add("sub.id", Long.parseLong(cri.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			ps.add("sub.name", cri.getName(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("sub.code", cri.getCode(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("sub.status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			if (cri.getCategory() != null) {
				ps.add("cat.id", Long.parseLong(cri.getCategory().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
				ps.add("cat.name", cri.getCategory().getName(), PrepareQuery.Operator.LIKE_ALL,
						PrepareQuery.Type.VARCHAR);
			}
			sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(), "sub.name", "asc");
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<SubCategory> list = new ArrayList<>();
			SubCategory b = null;
			while (rs.next()) {
				b = new SubCategory();
				b.setRowNumber(rs.getInt("row_num"));
				if (list.size() == 0)
					rtn.totalItem = rs.getInt("total");
				b.setId(rs.getString("sub_id"));
				b.setCode(rs.getString("sub_code"));
				b.setName(rs.getString("sub_name"));
				b.setStatus(rs.getInt("sub_status"));
				b.setCdate(rs.getDate("sub_cdate"));
				b.setMdate(rs.getDate("sub_mdate"));
				Category cat = new Category();
				cat.setId(rs.getString("cat_id"));
				cat.setName(rs.getString("cat_name"));
				b.setCategory(cat);
				list.add(b);
			}
			rtn.list = list;

			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}

	public ViewResult<SubCategory> delete(SubCategory data, Connection conn) throws NumberFormatException, Exception {
		ViewResult<SubCategory> rtn = new ViewResult<>();
		try {

			String sql = "DELETE FROM public.subcategory";
			PrepareQuery ps = new PrepareQuery();
			ps.add("id", Long.parseLong(data.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			ps.add("fk_category", Long.parseLong(data.getCategory().getId()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.ID);
			sql += ps.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			rtn.totalItem = stmt.executeUpdate();
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;

	}
}
