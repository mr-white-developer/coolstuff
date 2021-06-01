package com.jupitor.image.jpimageloader.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jupitor.image.jpimageloader.model.ImageData;
import com.jupitor.image.jpimageloader.model.ViewResult;
import com.jupitor.image.jpimageloader.utility.Common;

@Repository
public class Api {

	@Autowired
	Environment env;
	

	public ViewResult<ImageData> getPath(String value,String token){
		String apiurl = env.getProperty("api");
		String url = apiurl + "image/find/".concat(value);
		Gson gson = new Gson();
		Type type = new TypeToken<ViewResult<ImageData>>() {
		}.getType();
		return gson.fromJson(Common.httpGet(url, token), type);

	}
	public ViewResult<String> getPath(String token){
		if(token.equals("na")) token = "";
		String apiurl = env.getProperty("api");
		String url = apiurl + "image/getpath";
		Gson gson = new Gson();
		Type type = new TypeToken<ViewResult<String>>() {
		}.getType();
		return gson.fromJson(Common.httpGet(url, token), type);

	}
}
