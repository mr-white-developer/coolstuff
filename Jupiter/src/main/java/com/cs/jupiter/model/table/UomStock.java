package com.cs.jupiter.model.table;

import java.util.Date;

public class UomStock {

	private Long id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	private double specficPrice;
	private Long fkStock;
	private Long fkUom;
	private Long fkPriceType;
	
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
	public double getSpecficPrice() {
		return specficPrice;
	}
	public void setSpecficPrice(double specficPrice) {
		this.specficPrice = specficPrice;
	}
	public Long getFkStock() {
		return fkStock;
	}
	public void setFkStock(Long fkStock) {
		this.fkStock = fkStock;
	}
	public Long getFkUom() {
		return fkUom;
	}
	public void setFkUom(Long fkUom) {
		this.fkUom = fkUom;
	}
	public Long getFkPriceType() {
		return fkPriceType;
	}
	public void setFkPriceType(Long fkPriceType) {
		this.fkPriceType = fkPriceType;
	}
	
	
}
