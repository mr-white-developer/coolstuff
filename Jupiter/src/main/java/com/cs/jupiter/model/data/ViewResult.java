package com.cs.jupiter.model.data;

import java.util.ArrayList;
import java.util.List;


public class ViewResult <T>{
	public List<T> list = new ArrayList<T>();
	public T data ;
	public String message;
	public int status;
	public int totalItem = 0;
	
	public ViewResult(){
		
	}
	public ViewResult( T d, List<T> dlist){
		this.data = d;
		this.list = dlist;
		this.message = "";
		this.status = -1;
		this.totalItem = 0;
	}
	public ViewResult(int status,String mes){
		this.message = mes;
		this.status = status;
	}
	
}
