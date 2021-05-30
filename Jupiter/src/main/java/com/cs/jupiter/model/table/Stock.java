package com.cs.jupiter.model.table;

import java.util.ArrayList;
import java.util.List;
import com.cs.jupiter.model.data.ViewCredential;

public class Stock extends ViewCredential{

	private int rowNumber;
	private Category category;
	private SubCategory subCategory;
	private Brand brand;
	private PackType packType;
	private PackSize packSize;
	private List<StockHolding> stockHoldings;
	private List<UomStock> uomStocks;
	private List<ImageData> images;
	
	public Stock(){
		super();
		this.stockHoldings = new ArrayList<>();
		this.uomStocks = new ArrayList<>();
		this.images = new ArrayList<>();
	}
	public Stock(String id){
		super();
		this.setId(id);
		this.stockHoldings = new ArrayList<>();
		this.uomStocks = new ArrayList<>();
		this.images = new ArrayList<>();
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

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public PackType getPackType() {
		return packType;
	}

	public PackSize getPackSize() {
		return packSize;
	}

	public void setPackType(PackType packType) {
		this.packType = packType;
	}

	public void setPackSize(PackSize packSize) {
		this.packSize = packSize;
	}

	public List<UomStock> getUomStocks() {
		return uomStocks;
	}

	public void setUomStocks(List<UomStock> uomStocks) {
		this.uomStocks = uomStocks;
	}

	public List<StockHolding> getStockHoldings() {
		return stockHoldings;
	}

	public void setStockHoldings(List<StockHolding> stockHoldings) {
		this.stockHoldings = stockHoldings;
	}
	public List<ImageData> getImages() {
		return images;
	}
	public void setImages(List<ImageData> images) {
		this.images = images;
	}

	

	
}
