package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.BrandDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class BrandService implements CrudTemplate<Brand> {
	private static final Logger logger = LoggerFactory.getLogger(BrandOwnerService.class);
	@Autowired
	BrandDao brandDao;

	@Override
	public ViewResult<Brand> save(Brand data, RequestCredential crd, Connection conn) {
		Date curDate = new Date();
		ViewResult<Brand> vr;
		try {
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			if (data.getBrandOwner().getId().equals("-1")) {
				throw new Exception("BrandOwer is required");
			} else {
				vr = brandDao.insert(data, conn);

			}
		} catch (Exception e) {
			vr = new ViewResult<Brand>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Brand> deleteAll(RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> deleteById(Brand data, RequestCredential crd, Connection conn) {
		ViewResult<Brand> vr;
		try {
			vr = brandDao.delete(data, conn);
		} catch (Exception e) {
			vr = new ViewResult<Brand>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Brand> update(Brand data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> updateById(Brand data, RequestCredential crd, Connection conn) {

		ViewResult<Brand> vr;
		try {
			Date curDate = new Date();
			if (!data.getId().equals("-1") && !data.getId().equals("") && data.getId() != null) {
				data.setMdate(curDate);
				vr = brandDao.update(data, conn);
			} else {
				throw new Exception("Invalid BrandOwner");
			}

		} catch (Exception e) {
			vr = new ViewResult<Brand>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Brand> getAll(Brand data, RequestCredential crd, Connection conn) {
		ViewResult<Brand> vr;
		try {
			vr = brandDao.getAll(data, conn);
			vr.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			vr = new ViewResult<Brand>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Brand> inactive(Brand data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Brand> getById(String id, RequestCredential crd, Connection conn) {
		ViewResult<Brand> vr;
		try {
			Brand cri = new Brand();
			cri.setId(id);
			vr = brandDao.getAll(cri, conn);
			if (vr.list.size() > 0) {
				vr.data = vr.list.get(0);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				vr.data = null;
				vr.status = ComEnum.ErrorStatus.ClientError.getCode();
			}

		} catch (Exception e) {
			vr = new ViewResult<Brand>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Brand_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
