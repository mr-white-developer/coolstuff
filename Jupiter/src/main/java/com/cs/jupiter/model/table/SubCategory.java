package com.cs.jupiter.model.table;


import com.cs.jupiter.model.data.ViewCredential;

public class SubCategory extends ViewCredential {

	private int rowNumber;

	private Category category;
	
	public SubCategory (){
		this.rowNumber = -1;
	}
	public SubCategory (String id){
		super();
		this.setId(id);
		this.rowNumber = -1;
		this.category = new Category();
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
