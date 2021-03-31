package com.cs.jupiter.model.data;

import java.util.List;


public class ViewResult <T>{
	public List<T> list;
	public T data;
	public String message;
	public int status;
	public int totalItem = 0;
}
