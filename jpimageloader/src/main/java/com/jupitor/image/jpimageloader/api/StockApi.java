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
public class StockApi {

	@Autowired
	Environment env;
	
	public ViewResult<ImageData> getStockImagePath(String id,String token){
		String apiurl = env.getProperty("api");
		String url = apiurl + "stock-setup/get-image/".concat(id);
		Gson gson = new Gson();
		Type type = new TypeToken<ViewResult<ImageData>>() {
		}.getType();
		return gson.fromJson(Common.httpGet(url, token), type);

	}
}
