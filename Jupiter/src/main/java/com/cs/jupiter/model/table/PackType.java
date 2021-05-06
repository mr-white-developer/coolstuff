package com.cs.jupiter.model.table;

import com.cs.jupiter.model.data.ViewCredential;

public class PackType extends ViewCredential {
	private int rowNumber;
	
	public PackType(String id){
		super();
		this.setId(id);
	}
	public PackType(){
		super();
		this.rowNumber = 0;
			
	}
	
	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
}
