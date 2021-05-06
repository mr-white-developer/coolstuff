package com.cs.jupiter.model.table;
import com.cs.jupiter.model.data.ViewCredential;

public class StockHolding extends ViewCredential{
	private int rowNumber;
	private int qty;
	private Warehouse warehouse;
	private Stock stock;
	private Company company;
	private double rate;
	
	public StockHolding(){
		this.rowNumber = 0;
		this.qty = 0;
		this.rate=  0.0;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
