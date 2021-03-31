package com.cs.jupiter.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtility {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String convertDate_2db(Date d){
		return dateFormat.format(d);
	}
	
	public static Date convertDate_bydb(String d){
		try{
			return dateFormat.parse(d);
		}catch( Exception e){
			return new Date();
		}
	}
	
	public static String getCurrentDateTime(){
		return dateFormat.format(new Date());
	}
	
	public static String getFilterOr(String data, String dataType, String columnName) {
		String whereClause = "";
		if (!data.equals("") && !(data == null)) {
			data = data.replaceAll("'", "''");
			if (dataType.equals("eq")) {
				whereClause += " OR " + columnName + " LIKE '" + data + "'";
			} else if (dataType.equals("c")) {
				whereClause += " OR " + columnName + " LIKE '%" + data + "%'";
			} else if (dataType.equals("bw")) {
				whereClause += " OR " + columnName + " LIKE '" + data + "%'";
			} else if (dataType.equals("ew")) {
				whereClause += " OR " + columnName + " LIKE '%" + data + "'";
			}
		}
		return whereClause;
	}
	public static String getFilterAnd(String data, String dataType, String columnName) {
		String whereClause = "";
		if (!data.equals("") && !(data == null)) {
			data = data.replaceAll("'", "''");
			if (dataType.equals("eq")) {
				whereClause += " AND " + columnName + " LIKE '" + data + "'";
			} else if (dataType.equals("c")) {
				whereClause += " AND " + columnName + " LIKE '%" + data + "%'";
			} else if (dataType.equals("bw")) {
				whereClause += " AND " + columnName + " LIKE '" + data + "%'";
			} else if (dataType.equals("ew")) {
				whereClause += " AND " + columnName + " LIKE '%" + data + "'";
			}
		}
		return whereClause;
	}
	public static String getPegination(int current,int max){
		return " offset "+ current +" rows fetch next "+max+" rows only";
	}
	
	

}
