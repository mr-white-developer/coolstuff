package com.cs.jupiter.model.table;

import java.util.Date;

import com.cs.jupiter.utility.ComEnum;

public class Stock {

	private int rowNumber;
	private String id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	private String fkCategory;
	private Category category;
	private String fkSubCategory;
	private SubCategory subCategory;
	private String fkPackType;
	private String fkBrand;
	private Brand brand;
	
	public Stock(){
		this.id = "-1";
		this.code = "";
		this.name = "";
		this.status = ComEnum.RowStatus.Normal.getCode();
		this.cdate = new Date();
		this.mdate = new Date();
		this.fkCategory = "-1";
		this.fkSubCategory =  "-1";
		this.fkPackType = "-1";
		this.fkBrand = "-1";
		this.category = new Category();
		this.subCategory = new SubCategory();
		this.brand = new Brand();
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public String getFkCategory() {
		return fkCategory;
	}

	public void setFkCategory(String fkCategory) {
		this.fkCategory = fkCategory;
	}

	public String getFkSubCategory() {
		return fkSubCategory;
	}

	public void setFkSubCategory(String fkSubCategory) {
		this.fkSubCategory = fkSubCategory;
	}

	public String getFkPackType() {
		return fkPackType;
	}

	public void setFkPackType(String fkPackType) {
		this.fkPackType = fkPackType;
	}

	public String getFkBrand() {
		return fkBrand;
	}

	public void setFkBrand(String fkBrand) {
		this.fkBrand = fkBrand;
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

	
}
