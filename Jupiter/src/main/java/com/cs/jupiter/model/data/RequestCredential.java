package com.cs.jupiter.model.data;

public class RequestCredential {

	private String userId;
	private String role;
	private boolean gust;
	private String access_datetime;
	
	public int server_status;
	
	public RequestCredential(){
		
	}
	public RequestCredential(String userId, String role, boolean gust, String access_datetime) {
		super();
		this.userId = userId;
		this.role = role;
		this.gust = gust;
		this.access_datetime = access_datetime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isGust() {
		return gust;
	}
	public void setGust(boolean gust) {
		this.gust = gust;
	}
	public String getAccess_datetime() {
		return access_datetime;
	}
	public void setAccess_datetime(String access_datetime) {
		this.access_datetime = access_datetime;
	}
	
	
}
