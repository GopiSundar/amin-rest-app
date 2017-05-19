package com.amin.realty.service.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import com.mysql.jdbc.StringUtils;

public class SqlUtils {

	public static Boolean getBooleanFromYesNo(String yesNo){
		return ("Y").equalsIgnoreCase(yesNo);
	}

	public static DateTime getDateTime(Timestamp timeStamp){
		return (timeStamp!=null) ? new DateTime(timeStamp.getTime()):null;
	}
	
	public static Date getDate(Timestamp timeStamp){
		return (timeStamp!=null) ? new Date(timeStamp.getTime()):null;
	}
	
	public static Calendar getCalendar(Timestamp timeStamp){
		if(timeStamp!=null){
			Calendar calObj = Calendar.getInstance();
			calObj.setTimeInMillis(timeStamp.getTime());
			return calObj;
		}else{
			return null;
		}
		
	}
	
	public static Integer getIntFromStr(String str) {
		if (str == null)
			return null;
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	public static Double getDoubleFromStr(String str) {
		if (str == null)
			return null;
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return 0d;
		}
	}
	
	public static Float getFloatFromStr(String str) {
		if (str == null)
			return null;
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			return 0f;
		}
	}
	
	public static Long getLongFromStr(String str) {
		if (str == null)
			return null;
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	public static Calendar getCalendarObjFromStr(String str, String dateFormat,Locale locale){
		if (StringUtils.isNullOrEmpty(str))
			return null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
		try {
			cal.setTime(sdf.parse(str));
			return cal;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static Calendar getCalendarObjFromStr(String str, String dateFormat){
		if (StringUtils.isNullOrEmpty(str))
			return null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			cal.setTime(sdf.parse(str));
			return cal;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}		
	}
}
