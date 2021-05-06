package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Company;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class CompanyDao {
	@Autowired
	DataSource ds;

	public ViewResult<Company> insert(Company data,Connection conn) throws SQLException {

		ViewResult<Company> rtn = new ViewResult<>();
		try  {
			String sql = "INSERT INTO public.company"
					+ "(id, code, name, status, cdate, mdate, owner_name, email, phone)"
					+ "VALUES (?, ?, ?, ?, ?::date, ?::date, ?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setString(i++, data.getOwnerName());
			stmt.setString(i++, data.getEmail());
			stmt.setString(i++, data.getPhone());
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

	public ViewResult<Company> update(Company data,Connection conn) throws Exception {
		ViewResult<Company> rtn = new ViewResult<>();
		try  {

			String sql = "UPDATE public.company ";
			PrepareQuery p = new PrepareQuery();
			p.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			p.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			p.add("owner_name", data.getOwnerName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("email", data.getEmail(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("phone", data.getPhone(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
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

	public ViewResult<Company> delete(Company data,Connection conn) throws NumberFormatException, Exception {
		ViewResult<Company> rtn = new ViewResult<>();
		try  {
			String sql = "DELETE FROM public.company";
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

	public ViewResult<Company> getAll(Company cri,Connection conn) throws Exception {
		ViewResult<Company> rtn = new ViewResult<>();
		try  {
			String sql = "select row_number() over(order by c.name asc) as row_num,"
					+ "count(*) over() as total,* from company c";
			PrepareQuery ps = new PrepareQuery();
			ps.add("id", Long.parseLong(cri.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			ps.add("code", cri.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("name", cri.getName(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			ps.add("owner_name", cri.getOwnerName(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("email", cri.getEmail(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("phone", cri.getPhone(), PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(), "name", "asc");
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Company> list = new ArrayList<>();
			Company c = null;
			while (rs.next()) {
				c = new Company();
				c.setRowNumber(rs.getInt("row_num"));
				if (list.size() == 0)
					rtn.totalItem = rs.getInt("total");
				c.setId(rs.getString("id"));
				c.setCode(rs.getString("code"));
				c.setName(rs.getString("name"));
				c.setStatus(rs.getInt("status"));
				c.setCdate(rs.getDate("cdate"));
				c.setMdate(rs.getDate("mdate"));
				c.setOwnerName(rs.getString("owner_name"));
				c.setEmail(rs.getString("email"));
				c.setPhone(rs.getString("phone"));
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
