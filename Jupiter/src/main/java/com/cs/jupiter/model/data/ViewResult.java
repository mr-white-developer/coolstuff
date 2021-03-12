package com.cs.jupiter.model.data;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;


public class ViewResult <T>{
	public List<T> list;
	public T data;
	public String message;
	public int status;
	
	
	
}
