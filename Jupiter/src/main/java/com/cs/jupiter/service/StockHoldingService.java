package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.StockHoldingDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.StockHolding;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class StockHoldingService implements CrudTemplate<StockHolding>{
	@Autowired
	StockHoldingDao stockHoldingDao;
	
	
	@Override
	public ViewResult<StockHolding> save(StockHolding data, RequestCredential crd,Connection conn) {
		ViewResult<StockHolding> rtn ;
		Date today = new Date();
		try{
			if(data.getWarehouse().getId().equals("-1") || data.getWarehouse().getId().equals("")){
				throw new Exception("warehouse is required");
			}
			if(data.getStock().getId().equals("-1") || data.getStock().getId().equals("")){
				throw new Exception("warehouse is required");
			}
			data.setId(  Long.toString(KeyFactory.getId()));
			data.setCdate( today);
			data.setMdate( today);
			data.setStatus( ComEnum.RowStatus.Normal.getCode());
			rtn = stockHoldingDao.insert(data, conn);
			
		}catch(Exception e){
			rtn = new ViewResult<>();
			rtn.status = ComEnum.ErrorStatus.ServerError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public ViewResult<StockHolding> inactive(StockHolding data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<StockHolding> deleteAll(RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<StockHolding> deleteById(StockHolding data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<StockHolding> update(StockHolding data, RequestCredential crd,Connection conn) {
		ViewResult<StockHolding> rtn ;
		try{
			Date today = new Date();
			data.setMdate(today);
			if(data.getId().equals("-1") || data.getId().equals(""))
				throw new Exception("fail to save stockholding");
			rtn = stockHoldingDao.update(data, conn);
			
		}catch(Exception e){
			
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
		}
		return rtn;
	}

	@Override
	public ViewResult<StockHolding> updateById(StockHolding data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<StockHolding> getAll(StockHolding data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<StockHolding> getById(String id, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

}
