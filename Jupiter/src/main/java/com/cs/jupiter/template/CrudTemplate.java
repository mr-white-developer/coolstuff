package com.cs.jupiter.template;

import org.springframework.stereotype.Component;

import com.cs.jupiter.model.data.ViewResult;

@Component
public interface CrudTemplate <T>{

	public ViewResult<T> save();
	public ViewResult<T> deleteAll();
	public ViewResult<T> deleteById();
	public ViewResult<T> update();
	public ViewResult<T> updateById();
	
}
