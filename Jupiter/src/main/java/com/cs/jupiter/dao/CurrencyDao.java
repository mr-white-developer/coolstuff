package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Currency;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Repository
public class CurrencyDao {
	public ViewResult<Currency> insert(Currency data,Connection conn){
		ViewResult<Currency> rtn = new ViewResult<>(ComEnum.ErrorStatus.Success.getCode(),"");
		try{
			String sql = "INSERT INTO public.currency"
					+ "(id, code, name, status, cdate, mdate,rate)"
					+ "VALUES (?, ?, ?, ?, ?::date, ?::date, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setDouble(i++, data.getRate());
			if (stmt.executeUpdate() > 0)
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
			else
				rtn.status = ComEnum.ErrorStatus.ClientError.getCode();
		}catch(Exception e){
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}
	public ViewResult<Currency> getAll(Currency cri,Connection conn)  {
		ViewResult<Currency> rtn = new ViewResult<>();
		try  {
		String sql = "select row_number() over(order by name asc) as row_num,"
				+ "count(*) over() as total,*"
				+ " from currency";
		PrepareQuery ps = new PrepareQuery();
		ps.add("id", Long.parseLong(cri.getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
		ps.add("code", cri.getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
		ps.add("name", cri.getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
		ps.add("status", cri.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
		sql += ps.createWhereStatement(cri.getCurrentRow(), cri.getMaxRowsPerPage(),"name", "asc");
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		List<Currency> list = new ArrayList<>();
		Currency b = null;
		while (rs.next()){
			b = new Currency();
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

	public ViewResult<Currency> update(Currency data,Connection conn) throws Exception {
		ViewResult<Currency> rtn = new ViewResult<>();
		try  {

			String sql = "UPDATE public.currency ";
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

	public ViewResult<Currency> delete(Currency data,Connection conn) throws NumberFormatException, Exception {
		ViewResult<Currency> rtn = new ViewResult<>();
		try  {

			String sql = "DELETE FROM public.currency";
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
