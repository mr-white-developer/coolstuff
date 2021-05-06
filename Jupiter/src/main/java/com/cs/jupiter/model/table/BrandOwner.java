package com.cs.jupiter.model.table;

import java.util.ArrayList;
import java.util.List;

import com.cs.jupiter.model.data.ViewCredential;

public class BrandOwner extends ViewCredential{
	private int rowNumber;
	private List<Brand> brandList ;

	public BrandOwner(){
		this.rowNumber = 0;
		this.brandList = new ArrayList<Brand>();
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}


}
