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
import com.cs.jupiter.model.table.PackSize;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class PackSizeDao {
	
	@Autowired
	DataSource ds;

	public ViewResult<PackSize> insert(PackSize data,Connection conn) {
		ViewResult<PackSize> rtn = new ViewResult<>();
		
		try  {

			String sql = "INSERT INTO public.packsize("
					+ "id, code, name, status, mdate, cdate) VALUES (?, ?, ?, ?, ?::date, ?::date);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			if (stmt.executeUpdate() > 0) {
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
			}
		} catch (SQLException e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;

	}
	public ViewResult<PackSize> getAll(PackSize cri,Connection conn)  {
		ViewResult<PackSize> rtn = new ViewResult<>();
		try  {
		String sql = "select row_number() over(order by name asc) as row_num,"
				+ "count(*) over() as total,*"
				+ " from packsize";
		PrepareQuery ps = new PrepareQuery();
		ps.add("id", Long.parseLong(cri.getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
		ps.add("code", cri.getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
		ps.add("name", cri.getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
		ps.add("status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
		sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(),"name", "asc");
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		List<PackSize> list = new ArrayList<>();
		PackSize b = null;
		while (rs.next()){
			b = new PackSize();
			b.setRowNumber(rs.getInt("row_num"));
			if(list.size()==0) rtn.totalItem = rs.getInt("total");
			b.setId(rs.getString("id"));
			b.setCode(rs.getString("code"));
			b.setName(rs.getString("name"));
			b.setStatus(rs.getInt("status"));
			b.setCdate(rs.getDate("cdate"));
			b.setMdate(rs.getDate("mdate"));
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

	public ViewResult<PackSize> update(PackSize data,Connection conn) throws Exception {
		ViewResult<PackSize> rtn = new ViewResult<>();
		try  {

			String sql = "UPDATE public.packsize ";
			PrepareQuery p = new PrepareQuery();
			p.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			p.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
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

	public ViewResult<PackSize> delete(PackSize data,Connection conn) throws NumberFormatException, Exception {
		ViewResult<PackSize> rtn = new ViewResult<>();
		try  {

			String sql = "DELETE FROM public.packsize";
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
	
}
