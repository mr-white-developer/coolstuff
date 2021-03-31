package com.cs.jupiter.template;


import org.springframework.stereotype.Component;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewCredential;
import com.cs.jupiter.model.data.ViewResult;

@Component
public interface CrudTemplate <T>{

	public ViewResult<T> save(T data,RequestCredential crd);
	public ViewResult<T> deleteAll(RequestCredential crd);
	public ViewResult<T> deleteById(T data,RequestCredential crd);
	public ViewResult<T> update(T data,RequestCredential crd);
	public ViewResult<T> updateById(T data,RequestCredential crd);
	public ViewResult<T> getAll(ViewCredential data,RequestCredential crd);
	
}
