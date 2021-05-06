package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Company;
import com.cs.jupiter.model.table.Warehouse;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class WarehouseDao {
	@Autowired
	DataSource ds;

	public ViewResult<Warehouse> insert(Warehouse data, Connection conn) {
		ViewResult<Warehouse> rtn = new ViewResult<>();
		try {

			String sql = "INSERT INTO public.warehouse" + "(id, status, code, name, cdate, mdate,fk_company)"
					+ "VALUES (?, ?, ?, ?, ?::date, ?::date,?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setLong(i++, Long.parseLong(data.getCompany().getId()));

			if (stmt.executeUpdate() > 0)
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
			else
				rtn.status = ComEnum.ErrorStatus.ClientError.getCode();

		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;

	}

	public ViewResult<Warehouse> update(Warehouse data, Connection conn) {
		ViewResult<Warehouse> rtn = new ViewResult<>();
		try {

			String sql = "UPDATE public.warehouse ";
			PrepareQuery p = new PrepareQuery();
			p.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			p.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			if (data.getCompany() != null)
				p.add("fk_company", Long.parseLong(data.getCompany().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			sql += p.createSetStatement();
			p = new PrepareQuery();
			p.add("id", Long.parseLong(data.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);

			sql += p.createWhereStatement();
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

	public ViewResult<Warehouse> delete(Warehouse data, Connection conn) {
		ViewResult<Warehouse> rtn = new ViewResult<>();
		try {

			String sql = "DELETE FROM public.warehouse";
			PrepareQuery ps = new PrepareQuery();
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

	public ViewResult<Warehouse> getAll(Warehouse cri, Connection conn) {
		ViewResult<Warehouse> rtn = new ViewResult<>();
		try {

			String sql = "select row_number() over(order by wh.name asc) as row_num," + "count(*) over() as total,"
					+ "wh.id as wh_id," + "wh.code as wh_code," + "wh.name as wh_name," + "wh.status as wh_status,"
					+ "wh.cdate as wh_cdate," + "wh.mdate as wh_mdate," + "com.name as company_name,"
					+ "com.id as company_id" + " from warehouse wh inner join company com on com.id = wh.fk_company";
			PrepareQuery ps = new PrepareQuery();
			ps.add("wh.id", Long.parseLong(cri.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			ps.add("wh.code", cri.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("wh.name", cri.getName(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("wh.status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			if (cri.getCompany() != null) {
				ps.add("com.id", Long.parseLong(cri.getCompany().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
				ps.add("com.name", cri.getCompany().getName(), PrepareQuery.Operator.LIKE_ALL,
						PrepareQuery.Type.VARCHAR);
			}
			sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(), "wh.name", "asc");
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Warehouse> list = new ArrayList<>();
			Warehouse c = null;
			while (rs.next()) {
				c = new Warehouse();
				c.setRowNumber(rs.getInt("row_num"));
				if (list.size() == 0)
					rtn.totalItem = rs.getInt("total");
				c.setId(rs.getString("wh_id"));
				c.setCode(rs.getString("wh_code"));
				c.setName(rs.getString("wh_name"));
				c.setStatus(rs.getInt("wh_status"));
				c.setCdate(rs.getDate("wh_cdate"));
				c.setMdate(rs.getDate("wh_mdate"));
				Company comp = new Company();
				comp.setId(rs.getString("company_id"));
				comp.setName(rs.getString("company_name"));
				c.setCompany(comp);
				list.add(c);
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
}
