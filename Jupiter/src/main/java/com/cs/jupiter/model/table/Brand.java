package com.cs.jupiter.model.table;

import java.util.Date;


public class Brand{
	private int rowNumber;
	private String id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	private String fk_brandowner;
	
	private BrandOwner brandOwner;
	
	public Brand(){
		this.rowNumber = 0;
		this.id = "-1";
		this.code = "";
		this.name = "";
		this.status = -1;
		this.fk_brandowner = "-1";
		this.brandOwner = new BrandOwner();
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

	public String getFk_brandowner() {
		return fk_brandowner;
	}

	public void setFk_brandowner(String fk_brandowner) {
		this.fk_brandowner = fk_brandowner;
	}

	public BrandOwner getBrandOwner() {
		return brandOwner;
	}

	public void setBrandOwner(BrandOwner brandOwner) {
		this.brandOwner = brandOwner;
	}

	
}
