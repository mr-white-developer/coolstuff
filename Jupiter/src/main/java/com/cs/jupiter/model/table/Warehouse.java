package com.cs.jupiter.model.table;


import com.cs.jupiter.model.data.ViewCredential;

public class Warehouse extends ViewCredential{
	private int rowNumber;
	private Company company;
	
	public Warehouse(){
		this.rowNumber = 0;
	}
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
}
