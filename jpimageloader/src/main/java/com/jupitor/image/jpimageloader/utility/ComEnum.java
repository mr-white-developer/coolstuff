package com.jupitor.image.jpimageloader.utility;

import org.springframework.stereotype.Component;

@Component
public class ComEnum {
	public static enum ErrorStatus{
		NA(100,"NA"),
		Success(200,"Success"),
		ClientError(400,"ClientError"),
		ServerError(500,"ServerError"),
		DatabaseError(600,"DatabaseError");
		
		ErrorStatus(int code, String description) {
			this.code = code;
			this.description = description;
		}
		private final int code;
		private final String description;

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}
	public static enum RowStatus{
		Normal(1,"Normal"),
		Deleted(4,"Deleted"),
		ClientError(2,"Inactive");
		
		RowStatus(int code, String description) {
			this.code = code;
			this.description = description;
		}
		private final int code;
		private final String description;

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}
	public static enum Status{
		Default(0,"Default"),
		True(1,"True"),
		False(2,"False");
		
		Status(int code, String description) {
			this.code = code;
			this.description = description;
		}
		private final int code;
		private final String description;

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}
	public static enum PriceType{
		ByRatio(1,"By Ratio"),
		BySpecific(2,"By Specific");
		
		PriceType(int code, String description) {
			this.code = code;
			this.description = description;
		}
		private final int code;
		private final String description;

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}
	public static enum ServiceType{
		Stock(1,"Stock");
		ServiceType(int code, String description) {
			this.code = code;
			this.description = description;
		}
		private final int code;
		private final String description;

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}
}
