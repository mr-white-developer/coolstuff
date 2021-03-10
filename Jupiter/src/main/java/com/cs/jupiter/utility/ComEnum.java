package com.cs.jupiter.utility;

import org.springframework.stereotype.Component;

@Component
public class ComEnum {
	public static enum ErrorStatus{
		NA(100,"NA"),
		Success(200,"Success"),
		ClientError(400,"ClientError"),
		ServerError(500,"ServerError");
		
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
}
