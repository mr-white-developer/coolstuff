package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.BrandDao;
import com.cs.jupiter.dao.CategoryDao;
import com.cs.jupiter.dao.PackSizeDao;
import com.cs.jupiter.dao.PackTypeDao;
import com.cs.jupiter.dao.PricingDao;
import com.cs.jupiter.dao.StockDao;
import com.cs.jupiter.dao.StockHoldingDao;
import com.cs.jupiter.dao.SubCategoryDao;
import com.cs.jupiter.dao.UomStockDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.ImageData;
import com.cs.jupiter.model.table.Pricing;
import com.cs.jupiter.model.table.Stock;
import com.cs.jupiter.model.table.StockHolding;
import com.cs.jupiter.model.table.UomStock;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class StockService implements CrudTemplate<Stock> {
	private static final Logger logger = LoggerFactory.getLogger(StockService.class);
	private final String className = "StockService";

	@Autowired
	StockHoldingService stockHoldingService;
	
	@Autowired
	StockHoldingDao stockHoldingDao;
	

	@Autowired
	UomStockService uomStockService;
	
	@Autowired
	UomStockDao uomStockDao;
	
	@Autowired
	PricingDao pricingDao;

	@Autowired
	StockDao stkDao;
	
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
	
	@Autowired
	ImageService imgService;
	
	@Autowired
	Environment env;

	public ViewResult<Stock> saveStockSetup(Stock d, RequestCredential crd, Connection conn) {
		ViewResult<Stock> vr;
		try {
			conn.setAutoCommit(false);
			Date today = new Date();
			int status = -1;
			Stock stock = d;

			stock.setId(Long.toString(KeyFactory.getId()));
			stock.setCdate(today);
			stock.setMdate(today);
			stock.setStatus(ComEnum.RowStatus.Normal.getCode());

			status = save(stock, crd, conn).status;
			if (status != ComEnum.ErrorStatus.Success.getCode()) {
				throw new Exception("fail to save stock");
			}

			for (StockHolding sh : d.getStockHoldings()) {
				sh.getStock().setId(stock.getId());
				status = stockHoldingService.save(sh, crd, conn).status;
				if (status != ComEnum.ErrorStatus.Success.getCode()) {
					throw new Exception("fail to save stockholding");
				}
			}
			Pricing p;
			for (UomStock us : d.getUomStocks()) {
				us.getStock().setId(stock.getId());
				status = uomStockService.save(us, crd, conn).status;
				if (status != ComEnum.ErrorStatus.Success.getCode()) {
					throw new Exception("fail to save UomStock");
				}
				p = new Pricing();
				p.setId(Long.toString(KeyFactory.getId()));
				p.setCdate(today);
				p.setMdate(today);
				p.setUomStock(us);
				p.setCurrency(us.getCurrency());
				p.setPrice(us.getPrice());
				p.setRate(us.getRate());
				ViewResult<Pricing> rtnPricing = pricingDao.insert(p, conn);
				if(rtnPricing.status != ComEnum.ErrorStatus.Success.getCode()){
					throw new Exception(rtnPricing.message);
				}
				
			}
			conn.commit();
			vr = new ViewResult<Stock>(ComEnum.ErrorStatus.Success.getCode(), "");
			vr.data = d;
		} catch (Exception e) {
			vr = new ViewResult<Stock>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(className);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	public ViewResult<Stock> updateStockSetup(Stock data, RequestCredential crd, Connection conn) {
		ViewResult<Stock> rtn;
		try{
			Date today = new Date();
			conn.setAutoCommit(false);
			
			data.setMdate(today);
			ViewResult<Stock> stockResult = update(data,crd, conn);
			if(stockResult.status != ComEnum.ErrorStatus.Success.getCode()){
				throw new Exception(stockResult.message);
			}
			ViewResult<StockHolding> stockHoldResult;
			for (StockHolding sh : data.getStockHoldings()) {
				stockHoldResult = stockHoldingService.update(sh, crd, conn);
				if (stockHoldResult.status != ComEnum.ErrorStatus.Success.getCode()) {
					throw new Exception(stockHoldResult.message);
				}
			}
			Pricing p;
			for (UomStock us : data.getUomStocks()) {
				if (uomStockService.updateById(us, crd, conn).status != ComEnum.ErrorStatus.Success.getCode()) {
					throw new Exception("fail to save UomStock");
				}
				p = new Pricing();
				p.setMdate(today);
				p.setUomStock(us);
				p.setCurrency(us.getCurrency());
				p.setPrice(us.getPrice());
				p.setRate(us.getRate());
				p.setStatus(us.getStatus());
				ViewResult<Pricing> rtnPricing;
				if(us.getStatus() == ComEnum.RowStatus.Deleted.getCode()){
					rtnPricing = pricingDao.deleteByUomStock(p,conn);
				}else{
					rtnPricing = pricingDao.update(p, conn);
				}
				if(rtnPricing.status != ComEnum.ErrorStatus.Success.getCode()){
					throw new Exception(rtnPricing.message);
				}
			}
			conn.commit();
			rtn = new ViewResult<Stock>(ComEnum.ErrorStatus.Success.getCode(), "");
			rtn.data = data;
		}catch(Exception e){
			rtn = new ViewResult<Stock>();
			rtn.status = ComEnum.ErrorStatus.ServerError.getCode();
			rtn.message = e.getMessage();
			logger.info(className);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}
	@Override
	public ViewResult<Stock> save(Stock data, RequestCredential crd, Connection conn) {
		ViewResult<Stock> rtn;
		try {
			rtn = stkDao.insert(data, conn);
			if (rtn.status != ComEnum.ErrorStatus.Success.getCode()) {
				throw new Exception("Fail to insert stock");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtn = new ViewResult<Stock>(ComEnum.ErrorStatus.ServerError.getCode(), e.getMessage());
		}
		return rtn;
	}

	@Override
	public ViewResult<Stock> inactive(Stock data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Stock> deleteAll(RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Stock> deleteById(Stock data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Stock> update(Stock data, RequestCredential crd, Connection conn) {
		ViewResult<Stock> rtn;
		try{
			rtn = stkDao.update(data, conn);
			if(rtn.status != ComEnum.ErrorStatus.Success.getCode()){
				throw new Exception("Stock save fail");
			}
			
		}catch(Exception e){
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public ViewResult<Stock> updateById(Stock data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Stock> getAll(Stock data, RequestCredential crd, Connection conn) {
		ViewResult<Stock> rtn;
		try{
			rtn = stkDao.getAll(data, conn);
			if(rtn.status != ComEnum.ErrorStatus.Success.getCode()){
				
			}
			for(Stock s: rtn.list){
				s.getStockHoldings().addAll(stockHoldingDao.getAll(s.getId(), conn).list);
				s.getUomStocks().addAll(uomStockDao.getAll(s.getId(), conn).list);
				s.getImages().addAll(getStockImage(s.getId(), crd, conn).list);
			}
		}catch (Exception e){
			rtn = new ViewResult<Stock>();
			rtn.status = ComEnum.ErrorStatus.ServerError.getCode();
			rtn.message = e.getMessage();
			logger.info(className);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public ViewResult<Stock> getById(String id, RequestCredential crd, Connection conn) {
		ViewResult<Stock> rtn ;
		try{
			rtn = getAll(new Stock(id),crd,conn);
			if(rtn.status == ComEnum.ErrorStatus.Success.getCode() && rtn.list.size()==1){
				rtn.data = rtn.list.get(0);
				rtn.list = null;
				rtn.status = ComEnum.ErrorStatus.Success.getCode();
				System.out.println("image len:"+ rtn.data.getImages().size());
			}else{
				throw new Exception("no item with given id");
			}
		}catch(Exception e){
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}
	public ViewResult<ImageData> getStockImage(String id, RequestCredential crd, Connection conn){
		ViewResult<ImageData> rtn = null;
		try{
			ImageData img = new ImageData();
			img.setId("-1");
			img.setForeignKey(id);
			rtn = imgService.getAll(img, crd, conn);
		}catch(Exception e){
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}

}
