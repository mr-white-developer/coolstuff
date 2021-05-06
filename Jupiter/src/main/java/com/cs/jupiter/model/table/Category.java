package com.cs.jupiter.model.table;

import java.util.ArrayList;
import java.util.List;

import com.cs.jupiter.model.data.ViewCredential;

public class Category extends ViewCredential{

	private int rowNumber;
	private List<SubCategory> subCategories;
	
	
	public Category (){
		this.rowNumber = -1;
		this.subCategories = new ArrayList<>();
		
	}
	public Category (String id){
		super();
		this.setId(id);
		this.rowNumber = -1;
		this.subCategories = new ArrayList<>();
		
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public List<SubCategory> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(List<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}
	
	
}
