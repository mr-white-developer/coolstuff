package com.cs.jupiter.model.table;

import com.cs.jupiter.model.data.ViewCredential;


public class Brand extends ViewCredential{
	private int rowNumber;	
	private BrandOwner brandOwner;
	
	public Brand(){
		super();
		this.rowNumber = 0;
	}
	public Brand(String id){
		super();
		this.setId(id);
		this.rowNumber = 0;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public BrandOwner getBrandOwner() {
		return brandOwner;
	}
	public void setBrandOwner(BrandOwner brandOwner) {
		this.brandOwner = brandOwner;
	}


	
}
