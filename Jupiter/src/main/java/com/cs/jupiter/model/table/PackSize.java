package com.cs.jupiter.model.table;


import com.cs.jupiter.model.data.ViewCredential;

public class PackSize extends ViewCredential {
	private int rowNumber;

	public PackSize(){
		super();
		this.rowNumber = 0;
	}
	public PackSize(String id){
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
}
