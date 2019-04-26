package com.dawn.maple.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dawn
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String formatDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date StrToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        return sdf.parse(dateStr);
    }

	public static Date getDayBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	
	public static Date getDayBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	
	public static Date getDayEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

    public static Date getWeekBegin() {
        Calendar cal = Calendar.getInstance();
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1){
            cal.add(Calendar.DAY_OF_MONTH,-1);
        }
        //设置一个星期的第一天，按中国的习惯第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //获取当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期的差值
        cal.add(Calendar.DATE,cal.getFirstDayOfWeek() - day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1){
            cal.add(Calendar.DAY_OF_MONTH,-1);
        }
        //设置一个星期的第一天，按中国的习惯第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //获取当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期的差值
        cal.add(Calendar.DATE,cal.getFirstDayOfWeek() - day);
        cal.add(Calendar.DATE,6);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date getMonthBegin() {
        Calendar cal = Calendar.getInstance();
        // 获取前月的第一天
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        // 获取前月的最后一天
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    public static Date getYearBegin() {
        Calendar cal = Calendar.getInstance();
        // 获取前年的第一天
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    public static Date getYearEnd() {
        Calendar cal = Calendar.getInstance();
        // 获取前年的最后一天
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.DAY_OF_YEAR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
	
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(DateUtils.formatDate(date , DEFAULT_FORMAT));
		date = DateUtils.addDays(date, -3);
		date = DateUtils.getDayBegin(date);
		System.out.println(DateUtils.formatDate(date , DEFAULT_FORMAT));
		date = DateUtils.addHours(date, 9);
		System.out.println(DateUtils.formatDate(date , DEFAULT_FORMAT));
        System.out.println(DateUtils.formatDate(getWeekBegin() , DEFAULT_FORMAT));
        System.out.println(DateUtils.formatDate(getWeekEnd() , DEFAULT_FORMAT));
        System.out.println(DateUtils.formatDate(getMonthBegin() , DEFAULT_FORMAT));
        System.out.println(DateUtils.formatDate(getMonthEnd() , DEFAULT_FORMAT));
        System.out.println(DateUtils.formatDate(getYearBegin() , DEFAULT_FORMAT));
        System.out.println(DateUtils.formatDate(getYearEnd() , DEFAULT_FORMAT));
    }
}
