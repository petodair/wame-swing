package br.com.wame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
	public static String convertDateFormat(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String brDateConverter(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String dateHourConverter(String dateString) {
		Instant instant = Instant.parse(dateString);
		SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
		Date date = Date.from(instant);
		return outputFormat.format(date);
    }
	
	public static Date stringToDate(String dateString) {
		Instant instant = Instant.parse(dateString);
		Date date = Date.from(instant);
		return date;
    }
	
	//String dd-MM-YYYY para LocalDate
	public static LocalDate stringToLocalDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    LocalDate date = LocalDate.parse(dateString,formatter);
	    return date;
    }
	
	public static String getDayOfTheWeek(String dateString) {
		Instant instant = Instant.parse(dateString);
		Date date = Date.from(instant);
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DayOfWeek dayOfWeekEnum = localDate.getDayOfWeek();
		String dayOfWeekFormatted = dayOfWeekEnum.getDisplayName(TextStyle.FULL, Locale.getDefault());
		return dayOfWeekFormatted;
	}
}
