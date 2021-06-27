package com.cs.jupiter.utility;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.core.env.Environment;



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
	
	
	public static String generateImagePath(String serverPath,String domain) {
		try {
			String p = Pattern.quote(System.getProperty("file.separator"));
			String[] ary = serverPath.split(p);
			int index = Arrays.asList(ary).indexOf(domain);
			if (index == -1)
				return "";
			StringBuilder sb = new StringBuilder();
			for (int i = index; i < ary.length; i++) {
				sb.append("/");
				sb.append(ary[i].toString());
				if (i != ary.length - 1)
					sb.append("/");
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}

	}
	public static void outputLog(String log,Environment env) {
		if(env.getProperty("debug").equals("1")) {
			System.out.println(log);
		}
	}

}
