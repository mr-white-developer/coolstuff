package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs.jupiter.dao.WarehouseDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Warehouse;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class WarehouseService implements CrudTemplate<Warehouse> {
	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	private String info = "Warehouse_Info";

	@Autowired
	WarehouseDao dao;

	@Override
	public ViewResult<Warehouse> save(Warehouse data, RequestCredential crd,Connection conn) {
		Date curDate = new Date();
		ViewResult<Warehouse> vr ;
		try {
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);

			if (!data.getCompany().getId().equals("-1") && !data.getCompany().getId().equals("")) {
				vr = dao.insert(data, conn);
			}else{
				throw new Exception("Warehouse must attach with company");
			}

		} catch (Exception e) {
			vr = new ViewResult<Warehouse>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Warehouse> inactive(Warehouse data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Warehouse> deleteAll(RequestCredential crd,Connection conn) {
		return null;
	}

	@Override
	public ViewResult<Warehouse> deleteById(Warehouse data, RequestCredential crd,Connection conn) {
		ViewResult<Warehouse> vr;
		try{
			vr = dao.delete(data, conn);
		} catch (Exception e) {
			vr = new ViewResult<Warehouse>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Warehouse> update(Warehouse data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Warehouse> updateById(Warehouse data, RequestCredential crd,Connection conn) {
		Date curDate = new Date();
		ViewResult<Warehouse> vr ;

		try {
			
			if (!data.getId().equals("-1") && !data.getId().equals("") && data.getId() != null) {
				data.setMdate(curDate);
				vr = dao.update(data, conn);
				

			} else {
				throw new Exception("Invalid Company ID");
			}

		} catch (Exception e) {
			vr= new ViewResult<Warehouse>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Warehouse> getAll(Warehouse data, RequestCredential crd,Connection conn) {
		ViewResult<Warehouse> vr;
		try {
			vr = dao.getAll(data, conn);
			
		} catch (Exception e) {
			vr = new ViewResult<Warehouse>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Warehouse> getById(String id, RequestCredential crd,Connection conn) {
		ViewResult<Warehouse> vr;
		try {
			Warehouse cri = new Warehouse();
			cri.setId(id);
			vr = dao.getAll(cri, conn);
			if (vr.list.size() > 0) {
				vr.data = vr.list.get(0);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				throw new Exception("No record");
			}

		} catch (Exception e) {
			vr = new ViewResult<Warehouse>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
