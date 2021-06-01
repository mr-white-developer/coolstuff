package com.jupitor.image.jpimageloader.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jupitor.image.jpimageloader.api.Api;
import com.jupitor.image.jpimageloader.model.ImageData;
import com.jupitor.image.jpimageloader.model.ViewResult;
import com.jupitor.image.jpimageloader.utility.ComEnum;

@RestController
@RequestMapping("/img")
public class ImageController {
	@Autowired
	 Api api;

	@GetMapping(value = "/get/{name}/{token}")
	public void getImage(
			@PathVariable("name") String name,
			@PathVariable("token") String token,
			HttpServletRequest req, HttpServletResponse response) {
		ViewResult<String> rtn = api.getPath(token);
		
		if(rtn.status != ComEnum.ErrorStatus.Success.getCode()){
			return;
		}
		try {
			File file = new File(rtn.data + name);
			if (file.exists()) {
				String contentType = "application/octet-stream";
				response.setContentType(contentType);
				OutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(file);
				IOUtils.copy(in, out);
				out.close();
				in.close();
			} else {
				return;
			}
		} catch (Exception e) {
			return;
		}
	}
}
