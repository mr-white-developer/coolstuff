package com.cs.jupiter.model.table;

import java.util.Date;

public class StockHolding {

	private int rowNumber;
	private String id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	private int qty;
	private String fkWarehouse;
	private String fkStock;
	private Warehouse warehouse;
	private Stock stock;
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getFkWarehouse() {
		return fkWarehouse;
	}
	public void setFkWarehouse(String fkWarehouse) {
		this.fkWarehouse = fkWarehouse;
	}
	public String getFkStock() {
		return fkStock;
	}
	public void setFkStock(String fkStock) {
		this.fkStock = fkStock;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	
}
