package com.cs.jupiter.model.table;

import java.util.Date;

public class UomStock {

	private int rowNumber;
	private String id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	private double specficPrice;
	private String fkStock;
	private String fkUom;
	private String fkPriceType;
	private Stock stock;
	private Uom uom;
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
	public double getSpecficPrice() {
		return specficPrice;
	}
	public void setSpecficPrice(double specficPrice) {
		this.specficPrice = specficPrice;
	}
	public String getFkStock() {
		return fkStock;
	}
	public void setFkStock(String fkStock) {
		this.fkStock = fkStock;
	}
	public String getFkUom() {
		return fkUom;
	}
	public void setFkUom(String fkUom) {
		this.fkUom = fkUom;
	}
	public String getFkPriceType() {
		return fkPriceType;
	}
	public void setFkPriceType(String fkPriceType) {
		this.fkPriceType = fkPriceType;
	}
	public Stock getStock() {
		return stock;
	}
	public Uom getUom() {
		return uom;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public void setUom(Uom uom) {
		this.uom = uom;
	}
	
	
	
}
