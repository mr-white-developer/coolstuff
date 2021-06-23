package com.cs.jupiter.service;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cs.jupiter.dao.ImageDao;
import com.cs.jupiter.model.data.FileProperties;
import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.ImageData;
import com.cs.jupiter.template.CrudTemplate;
import com.cs.jupiter.utility.Authentication;
import com.cs.jupiter.utility.ComEnum;
import com.cs.jupiter.utility.KeyFactory;

@Service
public class ImageService implements CrudTemplate<ImageData> {

	@Autowired
	ImageDao dao;
	@Autowired
	Authentication auth;
	
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
		} catch (Exception e) {
			rtn = new ViewResult<>(ComEnum.ErrorStatus.ClientError.getCode(), e.getMessage());
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public ViewResult<ImageData> getById(String id, RequestCredential crd, Connection conn) {
		ViewResult<ImageData> rtn  =null;
		try{
			rtn = dao.getPathById(id, conn);
		}catch(Exception e){
			rtn = new ViewResult<>();
			rtn.status = ComEnum.ErrorStatus.ClientError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
		}
		return rtn;
	}
	
	public ViewResult<ImageData> prepareToSave(MultipartFile[] media,String pid,
			RequestCredential cred, Connection conn){
		ViewResult<ImageData> rtn  =null;
		Date d = new Date();
		try{
			String folder = env.getProperty("image-path");
			
			for(MultipartFile f: media){
				FileProperties pro = getProperties(f.getOriginalFilename());
				String fileName = generateImageName(pid) + "." + (pro.getFiletype().split("/")[1]);
				String filePath = folder + fileName;
				System.out.println(filePath);
				if(writeFile(f, filePath)){
					ImageData img = new ImageData();
					img.setId(pro.getId());
					img.setForeignKey(pid);
					img.setPath(filePath);
					img.setDefaults(pro.getIsDefault() == 1 ? true : false);
					img.setCdate(d);
					img.setMdate(d);
					img.setComment(pro.getComment().equals("NA") ? "" : pro.getComment());
					img.setCode(pro.getFiletype());
					img.setName(fileName);
					int status ;
					if(img.getId().equals("-1") ||
						 img.getId().equals("")){
						status = save(img,cred,conn).status;
					}else{
						status = update(img,cred,conn).status;
					}
					if(status ==  ComEnum.ErrorStatus.Success.getCode()){
						
					}else{
						File savedFile = new File(filePath);
						savedFile.delete();
					}
				}
			}
			rtn = new ViewResult<>();
			rtn.status = ComEnum.ErrorStatus.Success.getCode();
			
		}catch(Exception e){
			rtn = new ViewResult<>();
			rtn.status = ComEnum.ErrorStatus.ClientError.getCode();
			rtn.message = e.getMessage();
			e.printStackTrace();
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
	private String generateImageName(String stockId){
		return stockId + "_" + KeyFactory.generateRandom(5);
		
	}
	private FileProperties getProperties(String rawFileName){
		FileProperties pro = new FileProperties();
		String[] pa = rawFileName.split(":");
		if(pa.length == 5){
			pro.setId(pa[0]);
			pro.setFiletype(pa[1]);
			pro.setIsDefault(Integer.parseInt(pa[2]));
			pro.setColor(pa[3]);
			pro.setComment(pa[4]);
			return pro;
		}else return null;
	}
}
