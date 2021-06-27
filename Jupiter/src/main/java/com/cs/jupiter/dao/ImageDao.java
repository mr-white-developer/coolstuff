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
	public ViewResult<ImageData> update(ImageData data,Connection conn) throws Exception {
		ViewResult<ImageData> rtn = new ViewResult<>();
		try  {

			String sql = "UPDATE public.image ";
			PrepareQuery p = new PrepareQuery();
			
			p.add("code", data.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("name", data.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			p.add("def", data.isDefaults(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.BOOLEAN);
			p.add("path", data.getPath(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			p.add("comment", data.getComment(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			sql += p.createSetStatement();
			p = new PrepareQuery();  
			p.add("id", Long.parseLong(data.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += p.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			if(stmt.executeUpdate() > 0)
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
			else rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
		} catch (Exception e) {
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
				i.setName(rs.getString("name"));
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
	public ViewResult<ImageData> getPathById(String id,Connection conn){
		ViewResult<ImageData> rtn = new ViewResult<>();
		try{
			String sql = "select * from image";
			PrepareQuery q = new PrepareQuery();
			q.add("id", Long.parseLong(id), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += q.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ImageData i ;
			if(rs.next()){
				i = new ImageData();
				i.setId(rs.getString("id"));
				i.setStatus(rs.getInt("status"));
				i.setPath(rs.getString("path"));
				i.setComment(rs.getString("comment"));
				i.setDefaults(rs.getBoolean("def"));
				i.setForeignKey(rs.getString("foreign_key"));
				rtn.data = i;
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
				return rtn;
			}
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			return rtn;
		} catch (Exception e) {
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}
	public ViewResult<ImageData> delete(ImageData data,Connection conn){
		ViewResult<ImageData> rtn = new ViewResult<>();
		try{
			String sql = "delete from image";
			PrepareQuery q = new PrepareQuery();
			q.add("id", data.getId(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			q.add("foreign_key", data.getForeignKey(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += q.createWhereStatement();
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
	public ViewResult<ImageData> deleteById(String id,Connection conn){
		ViewResult<ImageData> rtn = new ViewResult<>();
		try{
			String sql = "delete from image where id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, Long.parseLong(id));
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
