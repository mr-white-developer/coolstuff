package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.ImageData;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.PrepareQuery;

@Repository
public class ImageDao {

	public ViewResult<ImageData> insert(ImageData data,Connection conn){
		ViewResult<ImageData> rtn = new ViewResult<>();
		try{
			String sql = "INSERT INTO public.image(code, name, status, def, path, comment, foreign_key)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int i = 1;
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setBoolean(i++, data.isDefaults());
			stmt.setString(i++, data.getPath());
			stmt.setString(i++, data.getComment());
			stmt.setLong(i++, Long.parseLong(data.getForeignKey()));
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
	public ViewResult<ImageData> getAll(ImageData data,Connection conn){
		ViewResult<ImageData> rtn = new ViewResult<>();
		try{
			String sql = "select * from image";
			PrepareQuery q = new PrepareQuery();
			q.add("id", data.getId(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			q.add("foreign_key", data.getForeignKey(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			//q.add("def", data.isDefaults(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.BOOLEAN);
			sql += q.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ImageData i ;
			while(rs.next()){
				i = new ImageData();
				i.setId(rs.getString("id"));
				i.setStatus(rs.getInt("status"));
				i.setPath(rs.getString("path"));
				i.setComment(rs.getString("comment"));
				i.setDefaults(rs.getBoolean("def"));
				i.setForeignKey(rs.getString("foreign_key"));
				rtn.list.add(i);
			}
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}
}
