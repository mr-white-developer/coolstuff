package com.cs.jupiter.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtility {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String convertDate_2db(Date d) {
		return dateFormat.format(d);
	}

	public static Date convertDate_bydb(String d) {
		try {
			return dateFormat.parse(d);
		} catch (Exception e) {
			return new Date();
		}
	}

	public static String getCurrentDateTime() {
		return dateFormat.format(new Date());
	}
}
