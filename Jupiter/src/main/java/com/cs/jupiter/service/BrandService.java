package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.BrandDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class BrandService implements CrudTemplate<Brand> {
	private static final Logger logger = LoggerFactory.getLogger(BrandOwnerService.class);
	@Autowired
	DataSource ds;

	@Autowired
	BrandDao brandDao;

	@Override
	public ViewResult<Brand> save(Brand data, RequestCredential crd) {
		Date curDate = new Date();
		ViewResult<Brand> vr = new ViewResult<Brand>();

		try (Connection myConn = ds.getConnection()) {

			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			if (data.getFk_brandowner().equals("-1")) {
				vr.status = ComEnum.ErrorStatus.ClientError.getCode();
				vr.message = "BrandOwner required";
				logger.info("Brand_Service");
				logger.error(vr.message);
			} else {
				if (brandDao.insert(data, myConn) > 0) {
					vr.status = ComEnum.ErrorStatus.Success.getCode();
				} else {
					vr.status = ComEnum.ErrorStatus.ClientError.getCode();
				}
			}

		} catch (Exception e) {
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {

		}
		return vr;
	}

	@Override
	public ViewResult<Brand> deleteAll(RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> deleteById(Brand data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> update(Brand data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> updateById(Brand data, RequestCredential crd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> getAll(ViewCredential data, RequestCredential crd) {
		ViewResult<Brand> vr = new ViewResult<Brand>();
		try (Connection myConn = ds.getConnection()) {
			vr = brandDao.getAll(data, myConn);
			vr.status = ComEnum.ErrorStatus.Success.getCode();
		}catch (Exception e) {
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
