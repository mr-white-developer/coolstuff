package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.SubCategoryDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.SubCategory;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class SubCategoryService implements CrudTemplate<SubCategory> {
	private static final Logger logger = LoggerFactory.getLogger(SubCategoryService.class);

	@Autowired
	SubCategoryDao SubCategoryDao;

	@Override
	public ViewResult<SubCategory> save(SubCategory data, RequestCredential crd,Connection conn) {
		Date curDate = new Date();
		ViewResult<SubCategory> vr;
		try {
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			if (data.getCategory().getId().equals("-1")) {
				throw new Exception("Category required");
			} else {
				vr = SubCategoryDao.insert(data, conn);
				if(vr.status == ComEnum.ErrorStatus.Success.getCode()){
					vr.data = data;
				}
			}
		} catch (Exception e) {
			vr = new ViewResult<SubCategory>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("SubCategory_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<SubCategory> deleteAll(RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<SubCategory> deleteById(SubCategory data, RequestCredential crd,Connection conn) {
		ViewResult<SubCategory> vr;
		try {
			vr = SubCategoryDao.delete(data, conn);
			vr.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			vr = new ViewResult<SubCategory>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("SubCategory_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<SubCategory> update(SubCategory data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<SubCategory> updateById(SubCategory data, RequestCredential crd,Connection conn) {
		Date curDate = new Date();
		ViewResult<SubCategory> vr;
		try {
			if (!data.getId().equals("-1") && !data.getId().equals("") && data.getId() != null) {
				data.setMdate(curDate);
				vr = SubCategoryDao.update(data, conn);
				vr.data = data;
			} else {
				throw new Exception("Invalid SubCategory");
			}

		} catch (Exception e) {
			vr = new ViewResult<SubCategory>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("SubCategory_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<SubCategory> getAll(SubCategory data, RequestCredential crd,Connection conn) {
		ViewResult<SubCategory> vr;
		try {
			vr = SubCategoryDao.getAll(data, conn);
		} catch (Exception e) {
			vr = new ViewResult<SubCategory>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("SubCategory_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<SubCategory> inactive(SubCategory data, RequestCredential crd,Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<SubCategory> getById(String id, RequestCredential crd,Connection conn) {
		ViewResult<SubCategory> vr;
		try {
			SubCategory cri = new SubCategory();
			cri.setId(id);
			vr = SubCategoryDao.getAll(cri, conn);
			if (vr.list.size() > 0) {
				vr.data = vr.list.get(0);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				vr.data = null;
				vr.status = ComEnum.ErrorStatus.ClientError.getCode();
			}

		} catch (Exception e) {
			vr = new ViewResult<SubCategory>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("SubCategory_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
