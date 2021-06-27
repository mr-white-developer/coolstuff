package com.cs.jupiter.model.data;

import java.util.Date;

public class ViewCredential {

	private int currentRow = -1;
	private int maxRowsPerPage = -1;
	private String orderby = "";
	private String sorting = "";
	
	private String id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	
	private boolean b1;
	
	
	
	public ViewCredential(){
		this.id = "-1";
		this.code = "";
		this.name = "";
		this.status = -1;
		this.b1 = false;
	}
	
	public int getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}
	public int getMaxRowsPerPage() {
		return maxRowsPerPage;
	}
	public void setMaxRowsPerPage(int maxRowsPerPage) {
		this.maxRowsPerPage = maxRowsPerPage;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
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

	public boolean isB1() {
		return b1;
	}

	public void setB1(boolean b1) {
		this.b1 = b1;
	}
	
	
}
