package com.cs.jupiter.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cs.jupiter.model.data.ViewResult;
import com.cs.jupiter.model.table.Category;
import com.cs.jupiter.model.table.ImageData;
import com.cs.jupiter.service.ImageService;
import com.cs.jupiter.utility.ComEnum;

@RestController
@RequestMapping("/upload")
public class FileUploadController extends RequestController {
	@Autowired
	DataSource ds;

	@Autowired
	ImageService service;
	
	@Autowired
	Environment env;

	@PostMapping(value = "/fileupload", consumes = { "multipart/form-data" })
	public ViewResult<ImageData> uploadFile(@RequestParam("uploadedfile") MultipartFile[] fileList,
			HttpServletRequest req, HttpServletResponse resp) {
		try (Connection con = ds.getConnection()) {
			return service.prepareToSave(fileList, getReqHeader(req, resp), con);
		} catch (SQLException e) {
			return new ViewResult<ImageData>(ComEnum.ErrorStatus.DatabaseError.getCode(), e.getMessage());
		}
	}

	@GetMapping(value = "/getimage")
	public void getImage(HttpServletRequest req, HttpServletResponse response) {
		try {
			File file = new File("C:/Users/kmz/Pictures/kmz copy.jpg");
			if (file.exists()) {
				String contentType = "application/octet-stream";
				response.setContentType(contentType);
				OutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(file);
				// copy from in to out
				IOUtils.copy(in, out);
				out.close();
				in.close();
			} else {
				System.out.println("file not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
