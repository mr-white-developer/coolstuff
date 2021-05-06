package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Company;
import com.cs.jupiter.model.table.StockHolding;
import com.cs.jupiter.model.table.Warehouse;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Repository
public class StockHoldingDao {
	@Autowired
	DataSource ds;

	public ViewResult<StockHolding> insert(StockHolding data,Connection conn) throws SQLException {
		ViewResult<StockHolding> rtn = new ViewResult<>();
		try  {

			String sql = "INSERT INTO public.stockholding"
					+ "(id, code, name, status, cdate, mdate, fk_warehouse, fk_stock, quantity,rate,fk_company) VALUES "
					+ "(?, ?, ?, ?, ?::date, ?::date, ?, ?, ?,?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setLong(i++, Long.parseLong(data.getWarehouse().getId()));
			stmt.setLong(i++, Long.parseLong(data.getStock().getId()));
			stmt.setInt(i++, data.getQty());
			stmt.setDouble(i++,  data.getRate());
			stmt.setLong(i++, Long.parseLong(data.getCompany().getId()));
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
	public ViewResult<StockHolding> getAll(String stockId,Connection conn)  {
		ViewResult<StockHolding> rtn = new ViewResult<>();
		try  {
			String sql = "select "
					+ " wh.id as wh_id,wh.code as wh_code,wh.name as wh_name,wh.status as wh_status,"
					+ " sh.id as sh_id,sh.code as sh_code,sh.name as sh_name,sh.status as sh_status,sh.fk_stock as sh_stockid,sh.quantity as sh_stkqty,sh.rate as sh_rate,"
					+ " com.id as com_id,com.code as com_code,com.name as com_name,com.owner_name as com_owner_name,com.email as com_email,com.phone as com_phone"
					+ " from stockholding sh inner join warehouse wh on wh.id = sh.fk_warehouse inner join company com on com.id=wh.fk_company"
					+ " where sh.fk_stock=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(stockId));
			ResultSet rs = stmt.executeQuery();
			StockHolding sh;
			while(rs.next()){
				sh = new StockHolding();
				sh.setId(rs.getString("sh_id"));
				sh.setStatus(rs.getInt("sh_status"));
				sh.setQty(rs.getInt("sh_stkqty"));
				sh.setRate(rs.getDouble("sh_rate"));
				
				Warehouse wh = new Warehouse();
				wh.setId(rs.getString("wh_id"));
				wh.setCode(rs.getString("wh_code"));
				wh.setName(rs.getString("wh_name"));
				wh.setStatus(rs.getInt("wh_status"));
				
				
				Company com = new Company();
				com.setId(rs.getString("com_id"));
				com.setCode(rs.getString("com_code"));
				com.setName(rs.getString("com_name"));
				com.setOwnerName(rs.getString("com_owner_name"));
				com.setEmail(rs.getString("com_email"));
				com.setPhone(rs.getString("com_phone"));
				
				wh.setCompany(com);
				sh.setWarehouse(wh);
				
				rtn.list.add(sh);
			}
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}

	public ViewResult<StockHolding> update(StockHolding data,Connection conn) throws Exception {
		ViewResult<StockHolding> rtn = new ViewResult<>();
		try {
			String sql = "UPDATE public.stockholding ";
			PrepareQuery p = new PrepareQuery();
			p.add("mdate", CommonUtility.convertDate_2db(data.getMdate()), PrepareQuery.Operator.EQUAL,
					PrepareQuery.Type.DATE);
			p.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			p.add("quantity", data.getQty(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			p.add("rate", data.getRate(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
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

	public ViewResult<StockHolding> delete(StockHolding data,Connection conn) throws NumberFormatException, Exception {
		ViewResult<StockHolding> rtn = new ViewResult<>();
		try  {

			String sql = "DELETE FROM public.stockholding";
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
