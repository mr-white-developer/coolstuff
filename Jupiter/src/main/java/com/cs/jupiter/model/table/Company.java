package com.cs.jupiter.model.table;

import java.util.ArrayList;
import java.util.List;

import com.cs.jupiter.model.data.ViewCredential;

public class Company extends ViewCredential{
	private int rowNumber;
	private String ownerName;
	private String email;
	private String phone;
	private List<Warehouse> warehouses;
	
	public Company(){
		this.rowNumber = 0;
		this.ownerName = "";
		this.email = "";
		this.phone = "";
		this.warehouses = new ArrayList<>();
		
	}
	public Company(String id){
		this.setId(id);
	}
	public int getRowNumber() {
		return rowNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
}
