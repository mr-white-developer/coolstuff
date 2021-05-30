package com.jupitor.image.jpimageloader.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImageController {

	@GetMapping(value = "/get/{id}/{token}")
	public void getImage(
			@PathVariable("id") String id,@PathVariable("token") String token,
			HttpServletRequest req, HttpServletResponse response) {
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
