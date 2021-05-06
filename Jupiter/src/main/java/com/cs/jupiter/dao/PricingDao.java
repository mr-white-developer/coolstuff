package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Repository;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Pricing;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;

@Repository
public class PricingDao {
	public ViewResult<Pricing> insert(Pricing data,Connection conn){
		ViewResult<Pricing> rtn = new ViewResult<>();
		try{
			String sql = "INSERT INTO public.pricing(id, code, name, status, cdate, mdate, fk_currency, fk_uomstock, price, rate)VALUES "
					+ "(?, ?, ?, ?, ?::date, ?::date, ?, ?, ?, ?);";
			int i = 1;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setLong(i++, Long.parseLong(data.getCurrency().getId()));
			stmt.setLong(i++, Long.parseLong(data.getUomStock().getId()));
			stmt.setDouble(i++, data.getPrice());
			stmt.setDouble(i++, data.getRate());
			if(stmt.executeUpdate()>0){
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
			}else{
				throw new Exception("Fail to save pricing");
			}
		}catch(Exception e){
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}
}
