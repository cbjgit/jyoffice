package com.jyoffice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GenOrderSN {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	
	/**
	 * 生成16位主键，时间戳+3位随机数
	 * 
	 * @return
	 */
	public static String getKey() {
		return System.currentTimeMillis() + "" + String.valueOf(Math.random()).substring(2, 5);

	}
	
	/**
	 * 生成15位非连续性订单号 yyMMdd+86400+1234 序号不清零
	 * 
	 * @return
	 */
	public static String get15Sn() {
		Calendar cal = Calendar.getInstance();
		StringBuffer sn = new StringBuffer(String.valueOf(cal.get(Calendar.YEAR)).substring(2));
		sn.append(String.valueOf(cal.get(Calendar.MONTH) + 1));
		sn.append(String.valueOf(cal.get(Calendar.DATE)));

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int time = 0;
		if (hour > 0)
			time += hour * 3600;
		int min = cal.get(Calendar.MINUTE);
		if (min > 0)
			time += min * 60;
		int sec = cal.get(Calendar.SECOND);
		if (sec > 0)
			time += sec;

		String timestr = String.valueOf(time);
		int len = timestr.length();
		for (int i = 0; i < 6 - len; i++) {
			timestr = "0" + timestr;
		}
		sn.append(timestr);
		sn.append(String.valueOf(Math.random()).substring(2, 6));
		return sn.toString();

	}
}
