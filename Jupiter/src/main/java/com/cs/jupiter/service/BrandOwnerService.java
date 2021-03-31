package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;

import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.BrandOwnerDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class BrandOwnerService implements CrudTemplate<BrandOwner> {
	private static final Logger logger = LoggerFactory.getLogger(BrandOwnerService.class);
	@Autowired
	DataSource ds;

	@Autowired
	BrandOwnerDao boDao;

	@Override
	public ViewResult<BrandOwner> save(BrandOwner data, RequestCredential cred) {
		Date curDate = new Date();
		ViewResult<BrandOwner> vr = new ViewResult<BrandOwner>();

		try {
			Connection myConn = ds.getConnection();
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			if (boDao.insert(data, myConn) > 0) {
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				vr.status = ComEnum.ErrorStatus.ClientError.getCode();
			}

		} catch (Exception e) {
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("BrandOwner_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<BrandOwner> deleteAll(RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> deleteById(BrandOwner data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> update(BrandOwner data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> updateById(BrandOwner data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> getAll(ViewCredential data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
