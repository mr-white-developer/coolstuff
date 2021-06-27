package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class BrandDao {
	@Autowired
	Environment env;
	
	public ViewResult<Brand> insert(Brand data, Connection conn) {
		ViewResult<Brand> rs = new ViewResult<>();
		try {
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
			stmt.setLong(i++, Long.parseLong(data.getBrandOwner().getId()));
			if (stmt.executeUpdate() > 0) {
				rs.status = ComEnum.ErrorStatus.Success.getCode();
			}
		} catch (SQLException e) {
			rs.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rs.message = e.getMessage();
		}
		return rs;

	}

	public ViewResult<Brand> update(Brand data, Connection conn) {
		ViewResult<Brand> rtn = new ViewResult<>();
		try {
			String sql = "UPDATE public.brand";
			PrepareQuery ps = new PrepareQuery();
			ps.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			ps.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			if (data.getBrandOwner() != null) {
				ps.add("fk_brandowner", Long.parseLong(data.getBrandOwner().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			}
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
		}
		return rtn;
	}

	public ViewResult<Brand> getAll(Brand cri, Connection conn) {
		ViewResult<Brand> rtn = new ViewResult<Brand>(null, new ArrayList<Brand>());
		try {
			String sql = "select row_number() over(order by b.name asc) as row_num," + "count(*) over() as total,"
					+ "b.id as b_id," + "b.code as b_code," + "b.name as b_name," + "b.status as b_status,"
					+ "b.cdate as b_cdate," + "b.mdate as b_mdate," + "bo.code as bo_code," + "bo.name as bo_name,"
					+ "bo.status as bo_status," + "bo.id as bo_id"
					+ " from brand b inner join brandowner bo on bo.id = b.fk_brandowner";
			PrepareQuery ps = new PrepareQuery();
			ps.add("b.status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);

			ps.add("b.id", Long.parseLong(cri.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			ps.add("b.name", cri.getName(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("b.code", cri.getCode(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			if (cri.getBrandOwner() != null) {
				ps.add("bo.status", cri.getBrandOwner().getStatus(), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.NUMBER);
				ps.add("bo.code", cri.getBrandOwner().getCode(), PrepareQuery.Operator.LIKE_ALL,
						PrepareQuery.Type.VARCHAR);
				ps.add("bo.name", cri.getBrandOwner().getName(), PrepareQuery.Operator.LIKE_ALL,
						PrepareQuery.Type.VARCHAR);
				ps.add("b.fk_brandowner", Long.parseLong(cri.getBrandOwner().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			}
			sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(), "b.name", "asc");
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			Brand b = null;
			while (rs.next()) {
				b = new Brand();
				b.setRowNumber(rs.getInt("row_num"));
				if (rtn.list.size() == 0)
					rtn.totalItem = rs.getInt("total");
				b.setId(rs.getString("b_id"));
				b.setCode(rs.getString("b_code"));
				b.setName(rs.getString("b_name"));
				b.setStatus(rs.getInt("b_status"));
				b.setCdate(rs.getDate("b_cdate"));
				b.setMdate(rs.getDate("b_mdate"));

				BrandOwner bo = new BrandOwner();
				bo.setCode(rs.getString("bo_code"));
				bo.setName(rs.getString("bo_name"));
				bo.setStatus(rs.getInt("bo_status"));
				bo.setId(rs.getString("bo_id"));
				b.setBrandOwner(bo);

				rtn.list.add(b);
			}
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}
	public ViewResult<Brand> read(String id, Connection conn) {
		ViewResult<Brand> rtn = new ViewResult<Brand>();
		try {
			String sql = "select row_number() over(order by b.name asc) as row_num," + "count(*) over() as total,"
					+ "b.id as b_id," + "b.code as b_code," + "b.name as b_name," + "b.status as b_status,"
					+ "b.cdate as b_cdate," + "b.mdate as b_mdate," + "bo.code as bo_code," + "bo.name as bo_name,"
					+ "bo.status as bo_status," + "bo.id as bo_id"
					+ " from brand b inner join brandowner bo on bo.id = b.fk_brandowner where b.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(id));
			CommonUtility.outputLog(sql, env);
			ResultSet rs = stmt.executeQuery();
			Brand b = null;
			if (rs.next()) {
				b = new Brand();
				b.setRowNumber(rs.getInt("row_num"));
				if (rtn.list.size() == 0)
					rtn.totalItem = rs.getInt("total");
				b.setId(rs.getString("b_id"));
				b.setCode(rs.getString("b_code"));
				b.setName(rs.getString("b_name"));
				b.setStatus(rs.getInt("b_status"));
				b.setCdate(rs.getDate("b_cdate"));
				b.setMdate(rs.getDate("b_mdate"));

				BrandOwner bo = new BrandOwner();
				bo.setCode(rs.getString("bo_code"));
				bo.setName(rs.getString("bo_name"));
				bo.setStatus(rs.getInt("bo_status"));
				bo.setId(rs.getString("bo_id"));
				b.setBrandOwner(bo);
				rtn.data = b;
			}
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}

	public ViewResult<Brand> delete(Brand data, Connection conn) {
		ViewResult<Brand> rtn = new ViewResult<>();
		try {
			String sql = "DELETE FROM public.brand";
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
