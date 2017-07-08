package com.jyoffice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	static SimpleDateFormat sdf = null;
	
	public static String formatDate(Date date,String format){
		sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String formatShotDate(Date date){
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static String formatLongDate(Date date){
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
