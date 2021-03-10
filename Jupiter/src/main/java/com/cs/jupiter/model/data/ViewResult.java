package com.cs.jupiter.model.data;

import java.util.ArrayList;
import java.util.List;
import com.cs.jupiter.utility.ComEnum;

public class ViewResult <T>{
	
	public List<T> list;
	public T data;
	public String message;
	public int status;
	
	public ViewResult(){
		this.list = new ArrayList<T>();
		this.data = null;
		this.message = ComEnum.ErrorStatus.NA.getDescription();
		this.status = ComEnum.ErrorStatus.NA.getCode();
	
	}
	
}
