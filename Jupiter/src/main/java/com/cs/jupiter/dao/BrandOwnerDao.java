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
import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Service
public class BrandOwnerDao {
	
	@Autowired
	DataSource ds;

	public ViewResult<BrandOwner> insert(BrandOwner data,Connection conn) throws SQLException {
		ViewResult<BrandOwner> rtn = new ViewResult<>();
		try {

			String sql = "INSERT INTO public.brandowner("
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
		}
		return rtn;

	}
	public ViewResult<BrandOwner> getAll(BrandOwner cri,Connection conn)  {
		ViewResult<BrandOwner> rtn = new ViewResult<>();
		try  {

		String sql = "select row_number() over(order by b.name asc) as row_num,"
				+ "count(*) over() as total,"
				+ "b.id as b_id,"
				+ "b.code as b_code,"
				+ "b.name as b_name,"
				+ "b.status as b_status,"
				+ "b.cdate as b_cdate,"
				+ "b.mdate as b_mdate"
				+ " from brandowner b";
		PrepareQuery ps = new PrepareQuery();
		ps.add("b.id", Long.parseLong(cri.getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
		ps.add("b.code", cri.getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
		ps.add("b.name", cri.getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
		ps.add("b.status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
		sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(),"b.name", "asc");
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		List<BrandOwner> list = new ArrayList<>();
		BrandOwner b = null;
		while (rs.next()){
			b = new BrandOwner();
			b.setRowNumber(rs.getInt("row_num"));
			if(list.size()==0) rtn.totalItem = rs.getInt("total");
			b.setId(rs.getString("b_id"));
			b.setCode(rs.getString("b_code"));
			b.setName(rs.getString("b_name"));
			b.setStatus(rs.getInt("b_status"));
			b.setCdate(rs.getDate("b_cdate"));
			b.setMdate(rs.getDate("b_mdate"));
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

	public ViewResult<BrandOwner> update(BrandOwner data,Connection conn) throws Exception {
		ViewResult<BrandOwner> rtn = new ViewResult<>();
		try  {

			String sql = "UPDATE public.brandowner ";
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

	public ViewResult<BrandOwner> delete(BrandOwner data,Connection conn) throws NumberFormatException, Exception {
		ViewResult<BrandOwner> rtn = new ViewResult<>();
		try  {

			String sql = "DELETE FROM public.brandowner";
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
