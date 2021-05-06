package com.cs.jupiter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.model.table.Category;
import com.cs.jupiter.model.table.PackSize;
import com.cs.jupiter.model.table.PackType;
import com.cs.jupiter.model.table.Stock;
import com.cs.jupiter.model.table.SubCategory;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.CommonUtility;
import com.cs.jupiter.utility.PrepareQuery;

@Repository
public class StockDao {
	@Autowired
	BrandDao brandDao;
	
	@Autowired
	CategoryDao catDao;
	
	@Autowired
	SubCategoryDao subCatDao;
	
	@Autowired
	PackTypeDao ptDao;
	
	@Autowired
	PackSizeDao psDao;
	
	public ViewResult<Stock> insert(Stock data,Connection conn){
		ViewResult<Stock> rtn = new ViewResult<>();
		try{
			String sql = "INSERT INTO public.stock"
					+ "(id, code, name, status, cdate, mdate, "
					+ "fk_category, fk_subcategory, fk_packtype, fk_brand, fk_packsize)"
					+ "VALUES (?, ?, ?, ?, ?::date, ?::date, ?, ?, ?, ?, ?)";
			PreparedStatement stmt  = conn.prepareStatement(sql);
			int i = 1;
			stmt.setLong(i++, Long.parseLong(data.getId()));
			stmt.setString(i++, data.getCode());
			stmt.setString(i++, data.getName());
			stmt.setInt(i++, data.getStatus());
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getCdate()));
			stmt.setString(i++, CommonUtility.convertDate_2db(data.getMdate()));
			stmt.setLong(i++, Long.parseLong(data.getCategory().getId()));
			stmt.setLong(i++, Long.parseLong(data.getSubCategory().getId()));
			stmt.setLong(i++, Long.parseLong(data.getPackType().getId()));
			stmt.setLong(i++, Long.parseLong(data.getBrand().getId()));
			stmt.setLong(i++, Long.parseLong(data.getPackSize().getId()));
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
	public ViewResult<Stock> getAll(Stock data,Connection conn){
		ViewResult<Stock> rtn = new ViewResult<>();
		try{
			String sql = "select row_number() over(order by stock.name asc) as row_num,count(*) over() as total, stock.* from stock"
					+ " inner join category cat on cat.id = stock.fk_category"
					+ " inner join subcategory subcat on subcat.id = fk_subcategory"
					+ " inner join packtype pt on pt.id = stock.fk_packtype"
					+ " inner join packsize ps on ps.id = stock.fk_packsize"
					+ " inner join brand on brand.id = stock.fk_brand ";
			PrepareQuery ps = new PrepareQuery();
			ps.add("stock.id", Long.parseLong(data.getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
			ps.add("stock.code", data.getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("stock.name", data.getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("stock.status", data.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			
			if(data.getCategory()!=null){
			ps.add("cat.id", Long.parseLong(data.getCategory().getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
			ps.add("cat.code", data.getCategory().getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("cat.name", data.getCategory().getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("cat.status", data.getCategory().getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			}
			if(data.getSubCategory()!=null){
			ps.add("subcat.id", Long.parseLong(data.getSubCategory().getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
			ps.add("subcat.code", data.getSubCategory().getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("subcat.name", data.getSubCategory().getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("subcat.status", data.getSubCategory().getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			}
			if(data.getPackType()!=null){
			ps.add("pt.id", Long.parseLong(data.getPackType().getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
			ps.add("pt.code", data.getPackType().getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("pt.name", data.getPackType().getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("pt.status", data.getPackType().getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			}
			if(data.getPackSize()!=null){
			ps.add("ps.id", Long.parseLong(data.getPackSize().getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
			ps.add("ps.code", data.getPackSize().getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("ps.name", data.getPackSize().getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("ps.status", data.getPackSize().getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			}
			if(data.getBrand()!=null){
			ps.add("brand.id", Long.parseLong(data.getBrand().getId()),PrepareQuery.Operator.EQUAL,PrepareQuery.Type.ID);
			ps.add("brand.code", data.getBrand().getCode(),PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			ps.add("brand.name", data.getBrand().getName(),PrepareQuery.Operator.LIKE_ALL, PrepareQuery.Type.VARCHAR);
			ps.add("brand.status", data.getBrand().getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			}
			sql += ps.createWhereStatement(data.getCurrentRow(), data.getMaxRowsPerPage(), "stock.name", "asc");
			System.out.println(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			Stock s ;
			while(rs.next()){
				s = new Stock();
				s.setRowNumber(rs.getInt("row_num"));
				if(rtn.list.size()==0) rtn.totalItem = rs.getInt("total");
				s.setId(rs.getString("id"));
				s.setCode(rs.getString("code"));
				s.setName(rs.getString("name"));
				s.setStatus(rs.getInt("status"));
				s.setCdate(rs.getDate("cdate"));
				s.setMdate(rs.getDate("mdate"));
				
				s.setBrand(brandDao.getAll(new Brand(rs.getString("fk_brand")), conn).list.get(0));
				s.setCategory(catDao.getAll(new Category(rs.getString("fk_category")), conn).list.get(0));
				s.setSubCategory(subCatDao.getAll(new SubCategory(rs.getString("fk_subcategory")), conn).list.get(0));
				s.setPackType(ptDao.getAll(new PackType(rs.getString("fk_packtype")), conn).list.get(0));
				s.setPackSize(psDao.getAll(new PackSize(rs.getString("fk_packsize")), conn).list.get(0));
				rtn.list.add(s);
			}
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		}catch(Exception e){
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}
	
	public ViewResult<Stock> update(Stock d,Connection conn){
		ViewResult<Stock> rtn = new ViewResult<>();
		try {
			String sql = "UPDATE public.stock ";
			PrepareQuery q = new PrepareQuery();
			q.add("code", d.getCode(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			q.add("name", d.getName(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.VARCHAR);
			q.add("status", d.getStatus(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.NUMBER);
			q.add("mdate", d.getMdate(), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.DATE);
			if (d.getCategory() != null)
				q.add("fk_category", Long.parseLong(d.getCategory().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			if (d.getSubCategory() != null)
				q.add("fk_subcategory", Long.parseLong(d.getSubCategory().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			if (d.getPackType() != null)
				q.add("fk_packtype", Long.parseLong(d.getPackType().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			if (d.getBrand() != null)
				q.add("fk_brand", Long.parseLong(d.getBrand().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			if (d.getPackSize() != null)
				q.add("fk_packsize", Long.parseLong(d.getPackSize().getId()), PrepareQuery.Operator.EQUAL,
						PrepareQuery.Type.ID);
			sql += q.createSetStatement();
			q = new PrepareQuery();
			q.add("id", Long.parseLong(d.getId()), PrepareQuery.Operator.EQUAL, PrepareQuery.Type.ID);
			sql += q.createWhereStatement();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			e.printStackTrace();
			rtn.status = ComEnum.ErrorStatus.DatabaseError.getCode();
			rtn.message = e.getMessage();
		}
		return rtn;
	}

}
