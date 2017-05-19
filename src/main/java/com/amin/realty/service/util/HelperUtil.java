package com.amin.realty.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.amin.realty.config.Constants;

public class HelperUtil {
	
	
	public static Object queryDataFormaterr(Object obj){
		return obj != null ? obj : "";
	}
	
	
	/**
	 * to mask the emailId by emailMaskEnabled flag
	 * @param email
	 * @param emailMaskEnabled
	 * @return
	 */
	public static String emailMask(String email, boolean emailMaskEnabled){
		if(emailMaskEnabled && StringUtils.isNotEmpty(email)) {
			
			email = email.replaceAll( Constants.EMAIL_MASK_REGEX, Constants.EMAIL_MASK_VAL );// to mask the emailId by using regular expression
			return email;
		}
		return email;
	}
	
	public static final DateTimeFormatter RFC_FORMATTER = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss Z");
	
	
	/**
	 * Converts input stream to byte array.
	 *
	 * @param is
	 *            Input stream that needs to be converted to ByteArray.
	 * @return ByteArray of the given InputStream.
	 * @throws IOException
	 *             Throws IOException
	 */
	public static byte[] convertInputStreamToByteArray(final InputStream is)
			throws IOException {

		
		byte[] retVal = null;

		if (is != null) {
			int len;
			final byte[] buffer = new byte[1024];
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				while ((len = is.read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				} // while

				baos.flush();
				retVal = baos.toByteArray();
			} catch (final Exception ex) {
				throw new IOException();
			} // try
		} // if ( is != null )

		return retVal;
	} // convertInputStreamToByteArray(is)
	
	
	public static String getSafeValue(String text){
		if (text==null|| ("NULL").equalsIgnoreCase(text) ){
			return null;
		}
		return text;
	}

	public static String RFCDateFormat(String dateStr) {
		
		return dateStr;
	}
	
	public static String getDateAsStringInRFCFormat(Date dateValue, String timeZone) {
		
		SimpleDateFormat rfcFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
		rfcFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		String rfcDateString = rfcFormat.format(dateValue);
		return rfcDateString;
	    
	}
	
		
	public static String getDateTimeAsStringInRFCFormat(Date dateValue) {
		
		SimpleDateFormat rfcFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
		String rfcDateString = rfcFormat.format(dateValue);
		return rfcDateString;
	    
	}
	
	public static String getDateInFormat(Date dateValue, String format) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String rfcDateString = dateFormat.format(dateValue);
		return rfcDateString;
	    
	}
	
	
	
	public static Date getDateInRFCFormat(String dateValue) throws Exception {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");			
			inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date inputDate = inputFormat.parse(dateValue);			
			
			return inputDate;
	    } catch (ParseException ex) {
	    	throw new Exception("Error occurred while converting date to RFC format. Invalid value : " + dateValue);
	    }
	}
	
	
	
	
	public static String getDateInFormat(DateTime dateValue, String format, DateTimeZone tz) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern(format);
		dateValue.withZoneRetainFields(tz);
		String dateString = dtfOut.print(dateValue);
		return dateString;
	    
	}
	
	
	
	public static Timestamp getTimestamp(DateTime dateTime){
		return new Timestamp(dateTime.getMillis());
	}
	
	public static Timestamp getTimestamp(DateTime dateTime, DateTimeZone tz){

		return new Timestamp(changeTimeZone(dateTime, tz).getMillis());
	}
	
	public static Timestamp getTimestamp(Date date, DateTimeZone tz){
		DateTime jodaTime = new DateTime(date);
		return new Timestamp(changeTimeZone(jodaTime, tz).getMillis());
	}
	
	public static Timestamp getTimestamp(Date dateTime){
		return new Timestamp(dateTime.getTime());
	}
	
	
	public static String getDollarValue(double value){		
		return "$" + String.valueOf(value);
	}
	
	public static String getMonthName(Integer monthNo){
		if(monthNo == null){
			return null;
		}
		
		return Month.of(monthNo).toString();
	}
	
	
	public static DateTime changeTimeZone(DateTime dateTime, DateTimeZone tz){
		LocalDateTime localDateTime = new LocalDateTime(dateTime.getMillis()); 
		DateTime newDateTime = localDateTime.toDateTime(tz);
		return newDateTime;
	}
	
	public static Date changeTimeZoneForDate(Date date, DateTimeZone tz){
		LocalDateTime localDateTime = new LocalDateTime(date.getTime()); 
		DateTime newDateTime = localDateTime.toDateTime(tz);
		return newDateTime.toDate();
	}
	
	
}
