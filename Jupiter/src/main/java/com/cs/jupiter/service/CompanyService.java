package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.CompanyDao;
import com.cs.jupiter.dao.WarehouseDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Company;
import com.cs.jupiter.model.table.Warehouse;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class CompanyService implements CrudTemplate<Company> {
	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	private String info = "Compnay_Info";


	@Autowired
	CompanyDao dao;
	
	@Autowired
	WarehouseDao whDao;
	@Autowired
	WarehouseService whService;
	

	@Override
	public ViewResult<Company> save(Company data, RequestCredential crd,Connection conn) {
		Date curDate = new Date();
		ViewResult<Company> vr;
		try  {
			conn.setAutoCommit(false);
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			vr = dao.insert(data, conn);
			for(Warehouse wh: data.getWarehouses()){
				wh.setCompany(new Company(data.getId()));
				if(whService.save(wh, crd, conn).status != ComEnum.ErrorStatus.Success.getCode()){
					throw new Exception("");
				}
			}
			conn.commit();
		} catch (Exception e) {
			vr = new ViewResult<Company>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Company_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Company> inactive(Company data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Company> deleteAll(RequestCredential crd,Connection conn) {
		return null;
	}

	@Override
	public ViewResult<Company> deleteById(Company data, RequestCredential crd,Connection conn) {
		ViewResult<Company> vr;
		try {
			vr = dao.delete(data, conn);
		} catch (Exception e) {
			vr = new ViewResult<Company>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Company> update(Company data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Company> updateById(Company data, RequestCredential crd,Connection conn) {
		Date curDate = new Date();
		ViewResult<Company> vr;

		try {

			if (!data.getId().equals("-1") && !data.getId().equals("") && data.getId() != null) {
				data.setMdate(curDate);
				vr = dao.update(data, conn);

			} else {
				throw new Exception("Invalid Company ID");
			}

		} catch (Exception e) {
			vr = new ViewResult<Company>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Company> getAll(Company data, RequestCredential crd,Connection conn) {
		ViewResult<Company> vr;
		try {
			vr = dao.getAll(data, conn);
			Warehouse w;
			for(Company company: vr.list){
				w = new Warehouse();
				w.setCompany(new Company(company.getId()));
				company.getWarehouses().addAll(whDao.getAll(w, conn).list);
			}

		} catch (Exception e) {
			vr = new ViewResult<Company>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Company> getById(String id, RequestCredential crd,Connection conn) {
		ViewResult<Company> vr;
		try  {
			Company cri = new Company();
			cri.setId(id);
			vr = dao.getAll(cri, conn);
			if (vr.list.size() > 0) {
				vr.data = vr.list.get(0);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				vr.data = null;
				vr.status = ComEnum.ErrorStatus.ClientError.getCode();
			}

		} catch (Exception e) {
			vr = new ViewResult<Company>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info(info);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
