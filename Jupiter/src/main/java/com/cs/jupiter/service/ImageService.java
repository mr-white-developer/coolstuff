package com.cs.jupiter.service;

import java.io.FileOutputStream;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cs.jupiter.dao.ImageDao;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.ImageData;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.ComEnum;

@Service
public class ImageService implements CrudTemplate<ImageData> {

	@Autowired
	ImageDao dao;
	
	@Autowired
	Environment env;

	@Override
	public ViewResult<ImageData> save(ImageData data, RequestCredential crd, Connection conn) {
		ViewResult<ImageData> rtn;
		try {
			rtn = dao.insert(data, conn);
		} catch (Exception e) {
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public ViewResult<ImageData> inactive(ImageData data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<ImageData> deleteAll(RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<ImageData> deleteById(ImageData data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<ImageData> update(ImageData data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<ImageData> updateById(ImageData data, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewResult<ImageData> getAll(ImageData data, RequestCredential crd, Connection conn) {
		ViewResult<ImageData> rtn;
		try {
			rtn = dao.getAll(data, conn);
//			rtn.list.forEach( e->{
//				String domain = env.getProperty("domain");
//				e.setPath(CommonUtility.generateImagePath(e.getPath(), domain));
//			});
		} catch (Exception e) {
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public ViewResult<ImageData> getById(String id, RequestCredential crd, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ViewResult<ImageData> prepareToSave(MultipartFile[] media,RequestCredential cred, Connection conn){
		ViewResult<ImageData> rtn  =null;
		try{
			for(MultipartFile f: media){
				//writeFile(f, path)
			}
		}catch(Exception e){
			
		}
		return rtn;
	}
	
	private Boolean writeFile(MultipartFile media,String path) {
		Boolean isCreated = false;
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(media.getBytes());
			fos.close();
			isCreated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCreated;
	}	

}
