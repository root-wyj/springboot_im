package com.wyj.springboot.im.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author ZhouHaiTao
 * @description 格式转换工具类
 * @version 1.0.0
 * @since 2014-6-16
 *
 */
public class DateUtil {

	// 获取系统时间（年-月-日 时:分:秒） 转为字符串
	public static String getDate() {
		GregorianCalendar g = new GregorianCalendar();
		String year = String.valueOf(g.get(GregorianCalendar.YEAR));
		String month = String.valueOf(g.get(GregorianCalendar.MONTH) + 1);
		String day = String.valueOf(g.get(GregorianCalendar.DAY_OF_MONTH));
		String hour = String.valueOf(g.get(GregorianCalendar.HOUR_OF_DAY));
		String minute = String.valueOf(g.get(GregorianCalendar.MINUTE));
		String second = String.valueOf(g.get(GregorianCalendar.SECOND));
		if (month.length() < 2) {
			month = "0" + month;
		}
		if (day.length() < 2) {
			day = "0" + day;
		}
		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		if (minute.length() < 2) {
			minute = "0" + minute;
		}
		if (second.length() < 2) {
			second = "0" + second;
		}
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	}

	// 字符串转Date
	public static Date getStringToDate(String dateStr, String format) {
		if (null == dateStr || dateStr.trim().length() <= 0) {
			return null;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date parse = sdf.parse(dateStr);
			return parse;
		} catch (Exception e) {
			return null;
		}
	}

	// Date转字符串
	public static String getDateToString(Date date, String format) {
		if (null == date) {
			return null;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
    public static long getDayTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        long endTime = calendar.getTime().getTime();
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTime().getTime();
        
        return endTime - startTime;
    }
    
    public static long getDaySecondTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        
        long endTime = calendar.getTime().getTime();
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        long startTime = calendar.getTime().getTime();
        
        return endTime - startTime;
    }

	public static void main(String[] args) {
		System.out.println(getDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
}
