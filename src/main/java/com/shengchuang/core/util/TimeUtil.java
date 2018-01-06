package com.shengchuang.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class TimeUtil {

	public static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public static final ThreadLocal<DateFormat> DEFAULT_DATE_TIME_FORMATTER = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static final long MILLIS_PER_SECOND = 1000L;
	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	/**
	 * 获取今天的固定时间
	 * 
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return
	 */
	public static Date getTimeOfToday(int hour, int minute, int second) {
		return setTime(new Date(), hour, minute, second);
	}

	/**
	 * 日期不变设置时分秒
	 * 
	 * @param date
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return
	 */
	public static Date setTime(Date date, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 今天的开始时刻
	 * 
	 * @return
	 */
	public static Date getStartTimeToday() {
		return getTimeOfToday(0, 0, 0);
	}

	/**
	 * 今天的结束时刻
	 * 
	 * @return
	 */
	public static Date getOverTimeToday() {
		long time = getTimeOfToday(0, 0, 0).getTime() + MILLIS_PER_DAY;
		return new Date(time);
	}

	/**
	 * 获取某天的开始时刻
	 * 
	 * @return
	 */
	public static Date getStartTimeOfDate(Date date) {
		return setTime(date, 0, 0, 0);
	}

	/**
	 * 某天的结束时刻
	 * 
	 * @return
	 */
	public static Date getOverTimeOfDate(Date date) {
		long time = setTime(date, 0, 0, 0).getTime() + MILLIS_PER_DAY;
		return new Date(time);
	}

	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static Date add(int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	public static Date addYear(int amount) {
		return add(Calendar.YEAR, amount);
	}

	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	public static Date addDay(int amount) {
		return add(Calendar.DATE, amount);
	}

	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	public static Date addMonth(int amount) {
		return add(Calendar.MONTH, amount);
	}

	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	public static Date addHour(int amount) {
		return add(Calendar.HOUR, amount);
	}

	public static Date addMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	public static Date addMinute(int amount) {
		return add(Calendar.MINUTE, amount);
	}

	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	public static Date addSecond(int amount) {
		return add(Calendar.SECOND, amount);
	}

	public static Date addMillis(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	public static Date addMillis(int amount) {
		return add(Calendar.MILLISECOND, amount);
	}

	public static Date parseDate(String source) throws ParseException {
		return TimeUtil.DATE_FORMAT.get().parse(source);
	}

}
