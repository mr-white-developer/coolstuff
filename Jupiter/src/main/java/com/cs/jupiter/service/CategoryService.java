package com.cs.jupiter.service;

import java.sql.Connection;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.jupiter.dao.CategoryDao;
import com.cs.jupiter.dao.SubCategoryDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Category;
import com.cs.jupiter.model.table.SubCategory;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class CategoryService implements CrudTemplate<Category> {
	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	CategoryDao catDao;

	@Autowired
	SubCategoryService subService;

	@Autowired
	SubCategoryDao subDao;

	@Override
	public ViewResult<Category> save(Category data, RequestCredential cred, Connection conn) {
		Date curDate = new Date();
		ViewResult<Category> vr;

		try {
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			vr = catDao.insert(data, conn);

			if (vr.status == ComEnum.ErrorStatus.Success.getCode()) {
				return vr;
			} else {
				vr.status = ComEnum.ErrorStatus.DatabaseError.getCode();
				logger.info("Category_Dao");
				logger.error(vr.message);
			}
		} catch (Exception e) {
			vr = new ViewResult<>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Category_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Category> deleteAll(RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Category> deleteById(Category data, RequestCredential crd, Connection conn) {
		ViewResult<Category> vr;
		try {
			conn.setAutoCommit(false);
			SubCategory sub = new SubCategory();
			sub.setCategory(data);
			if (subService.deleteById(sub, crd, conn).status == ComEnum.ErrorStatus.Success.getCode()) {
				vr = catDao.delete(data, conn);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
				conn.commit();
			}else{
				throw new Exception("Fail to delete subcategory");
			}
			
		} catch (Exception e) {
			vr = new ViewResult<>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Category_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Category> update(Category data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Category> updateById(Category data, RequestCredential crd, Connection conn) {
		Date curDate = new Date();
		ViewResult<Category> vr;

		try {
			if (!data.getId().equals("-1") && !data.getId().equals("") && data.getId() != null) {
				data.setMdate(curDate);
				vr = catDao.update(data, conn);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				throw new Exception("Invalid Category");
			}
		} catch (Exception e) {
			vr = new ViewResult<Category>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Category_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Category> getAll(Category data, RequestCredential crd, Connection conn) {
		ViewResult<Category> vr;
		try {
			vr = catDao.getAll(data, conn);
			if (data.isB1()) {
				SubCategory sc;
				for (Category c : vr.list) {
					sc = new SubCategory();
					sc.setCategory(c);
					c.getSubCategories().addAll(subDao.getAll(sc, conn).list);
				}
			}

			vr.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			vr = new ViewResult<Category>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Category_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<Category> inactive(Category data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<Category> getById(String id, RequestCredential crd, Connection conn) {
		ViewResult<Category> vr;
		try {
			Category cri = new Category();
			cri.setId(id);
			vr = catDao.getAll(cri, conn);
			if (vr.list.size() > 0) {
				vr.data = vr.list.get(0);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
				SubCategory sub = new SubCategory();
				sub.setCategory(new Category(id));
				ViewResult<SubCategory> subResult = subDao.getAll(sub, conn);
				if (subResult.status == ComEnum.ErrorStatus.Success.getCode()) {
					vr.data.getSubCategories().addAll(subResult.list);
				}

			} else {
				throw new Exception("No record");
			}

		} catch (Exception e) {
			vr = new ViewResult<Category>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("Category_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
