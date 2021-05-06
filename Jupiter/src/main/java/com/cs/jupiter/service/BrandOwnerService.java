package com.cs.jupiter.service;
import java.sql.Connection;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs.jupiter.dao.BrandOwnerDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Brand;
import com.cs.jupiter.model.table.BrandOwner;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class BrandOwnerService implements CrudTemplate<BrandOwner> {
	private static final Logger logger = LoggerFactory.getLogger(BrandOwnerService.class);

	@Autowired
	BrandOwnerDao boDao;
	
	@Autowired
	BrandService brandService;

	@Override
	public ViewResult<BrandOwner> save(BrandOwner data, RequestCredential cred, Connection conn) {
		Date curDate = new Date();
		ViewResult<BrandOwner> vr ;
		try {
			data.setId(Long.toString(KeyFactory.getId()));
			data.setStatus(ComEnum.RowStatus.Normal.getCode());
			data.setCdate(curDate);
			data.setMdate(curDate);
			vr = boDao.insert(data, conn);
		} catch (Exception e) {
			vr  = new ViewResult<BrandOwner>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("BrandOwner_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<BrandOwner> deleteAll(RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> deleteById(BrandOwner data, RequestCredential crd, Connection conn) {
		ViewResult<BrandOwner> vr ;
		try {
			vr = boDao.delete(data, conn) ;
			vr.status = ComEnum.ErrorStatus.Success.getCode();
		} catch (Exception e) {
			vr = new ViewResult<BrandOwner>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("BrandOwner_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<BrandOwner> update(BrandOwner data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> updateById(BrandOwner data, RequestCredential crd, Connection conn) {
		Date curDate = new Date();
		ViewResult<BrandOwner> vr ;

		try {
			if (!data.getId().equals("-1") && !data.getId().equals("") && data.getId() != null) {
				data.setMdate(curDate);
				vr = boDao.update(data, conn);
				
			} else {
				throw new Exception("Invalid BrandOwner");
			}

		} catch (Exception e) {
			vr = new ViewResult<BrandOwner>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("BrandOwner_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<BrandOwner> getAll(BrandOwner data, RequestCredential crd, Connection conn) {
		ViewResult<BrandOwner> vr;
		try {
			vr = boDao.getAll(data, conn);
			if(data.isB1()){ //require brand data
				Brand b;
				for (BrandOwner bo : vr.list) {
					b = new Brand();
					b.setBrandOwner(bo);
					System.out.println(bo.getId());
					bo.getBrandList().addAll(brandService.getAll(b, crd, conn).list);
				}
			}
			
		} catch (Exception e) {
			vr  = new ViewResult<BrandOwner>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("BrandOwner_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

	@Override
	public ViewResult<BrandOwner> inactive(BrandOwner data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<BrandOwner> getById(String id, RequestCredential crd, Connection conn) {
		ViewResult<BrandOwner> vr ;
		try {
			BrandOwner cri = new BrandOwner();
			cri.setId(id);
			vr = boDao.getAll(cri, conn);
			if (vr.list.size() > 0) {
				vr.data = vr.list.get(0);
				vr.status = ComEnum.ErrorStatus.Success.getCode();
			} else {
				vr.data = null;
				vr.status = ComEnum.ErrorStatus.ClientError.getCode();
			}

		} catch (Exception e) {
			vr  = new ViewResult<BrandOwner>();
			vr.status = ComEnum.ErrorStatus.ServerError.getCode();
			vr.message = e.getMessage();
			logger.info("BrandOwner_Service");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return vr;
	}

}
