package com.cs.jupiter.model.table;

import java.util.Date;

import com.cs.jupiter.utility.ComEnum;

public class Stock {

	private Long id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	private Long fkCategory;
	private Long fkSubCategory;
	private Long fkPackType;
	private Long fkBrand;
	
	public Stock(){
		this.id = (long) 0;
		this.code = "";
		this.name = "";
		this.status = ComEnum.RowStatus.Normal.getCode();
		this.cdate = new Date();
		this.mdate = new Date();
		this.fkCategory = (long) 0;
		this.fkSubCategory =  (long) 0;
		this.fkPackType = (long) 0;
		this.fkBrand = (long) 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getFkCategory() {
		return fkCategory;
	}

	public void setFkCategory(Long fkCategory) {
		this.fkCategory = fkCategory;
	}

	public Long getFkSubCategory() {
		return fkSubCategory;
	}

	public void setFkSubCategory(Long fkSubCategory) {
		this.fkSubCategory = fkSubCategory;
	}

	public Long getFkPackType() {
		return fkPackType;
	}

	public void setFkPackType(Long fkPackType) {
		this.fkPackType = fkPackType;
	}

	public Long getFkBrand() {
		return fkBrand;
	}

	public void setFkBrand(Long fkBrand) {
		this.fkBrand = fkBrand;
	}

	
}
