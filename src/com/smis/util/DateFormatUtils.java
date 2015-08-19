package com.smis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	static {
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	public static String format(Date date) {
		return simpleDateFormat.format(date);
	}

	public static Date parse(String dateString) throws ParseException {
		return simpleDateFormat.parse(dateString);
	}

}