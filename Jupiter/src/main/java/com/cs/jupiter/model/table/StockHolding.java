package com.cs.jupiter.model.table;

import java.util.Date;

public class StockHolding {

	private Long id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;	
	private int qty;
	private Long fkWarehouse;
	private Long fkStock;
	
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Long getFkWarehouse() {
		return fkWarehouse;
	}
	public void setFkWarehouse(Long fkWarehouse) {
		this.fkWarehouse = fkWarehouse;
	}
	public Long getFkStock() {
		return fkStock;
	}
	public void setFkStock(Long fkStock) {
		this.fkStock = fkStock;
	}
	
}
