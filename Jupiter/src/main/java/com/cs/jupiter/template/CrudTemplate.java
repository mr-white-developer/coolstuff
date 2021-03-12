package com.cs.jupiter.template;

import java.sql.Connection;

import org.springframework.stereotype.Component;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.model.data.ViewResult;

@Component
public interface CrudTemplate <T>{

	public ViewResult<T> save(T data,Connection conn,RequestCredential crd);
	public ViewResult<T> deleteAll(Connection conn,RequestCredential crd);
	public ViewResult<T> deleteById(T data,Connection conn,RequestCredential crd);
	public ViewResult<T> update(T data,Connection conn,RequestCredential crd);
	public ViewResult<T> updateById(T data,Connection conn,RequestCredential crd);
	
}
