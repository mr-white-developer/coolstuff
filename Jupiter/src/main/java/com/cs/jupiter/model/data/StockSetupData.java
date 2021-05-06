package com.cs.jupiter.model.data;

import java.util.ArrayList;
import java.util.List;

import com.cs.jupiter.model.table.Currency;
import com.cs.jupiter.model.table.Stock;
import com.cs.jupiter.model.table.StockHolding;
import com.cs.jupiter.model.table.UomStock;

public class StockSetupData {
	private List<StockHolding> stockHolding;
	private List<UomStock> uomStocks;
	private Currency currency;
	private Stock stock;
	
	public StockSetupData(){
		this.stockHolding = new ArrayList<>();
		this.uomStocks =  new ArrayList<>();
		this.stock = new Stock();
		this.currency = new Currency();
	}

	public List<StockHolding> getStockHolding() {
		return stockHolding;
	}

	public List<UomStock> getUomStocks() {
		return uomStocks;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStockHolding(List<StockHolding> stockHolding) {
		this.stockHolding = stockHolding;
	}

	public void setUomStocks(List<UomStock> uomStocks) {
		this.uomStocks = uomStocks;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	
}
