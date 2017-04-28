package bigdata.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeRange {
	
	private TimeRangeUnit timeWidth;
	private TimeRangeUnit timeFragment;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	private TimeRange(TimeRangeUnit timeWidth, TimeRangeUnit timeFragment, int year, int month,  int day,
			int hour, int minute) {
		super();
		this.timeWidth = timeWidth;
		this.timeFragment = timeFragment;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	

	
	public static TimeRange createBy_Date_Hour(int year,int month,int day){
		return new TimeRange(TimeRangeUnit.DAY,TimeRangeUnit.HOUR, year, month, day, 0, 0);
	}
	
	public static TimeRange createBy_Month_Day(int year,int month){
		return new TimeRange(TimeRangeUnit.MONTH,TimeRangeUnit.DAY, year, month, 1, 0, 0);
	}
	

	
	public static TimeRange createBy_Year_Month(int year){
		return new TimeRange(TimeRangeUnit.YEAR,TimeRangeUnit.MONTH,year,0,0,0,0);
	}
	
	public static TimeRange createBy_Hour_Minute(int year,int month,int date,int hour){
		return new TimeRange(TimeRangeUnit.HOUR, TimeRangeUnit.MINUTE, year, month, date, hour, 0);
	}
	
	public String getStartTimeStr(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
	}
	
	public String getEndTimeStr(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		
		if(timeWidth==TimeRangeUnit.YEAR){
			cal.add(Calendar.YEAR, 1);
		}else if(timeWidth==TimeRangeUnit.MONTH){
			cal.add(Calendar.MONTH, 1);
		}else if(timeWidth==TimeRangeUnit.DAY){
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
	}



	public TimeRangeUnit getTimeWidth() {
		return timeWidth;
	}


	public void setTimeWidth(TimeRangeUnit timeWidth) {
		this.timeWidth = timeWidth;
	}


	public TimeRangeUnit getTimeFragment() {
		return timeFragment;
	}


	public void setTimeFragment(TimeRangeUnit timeFragment) {
		this.timeFragment = timeFragment;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}




	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public int getHour() {
		return hour;
	}


	public void setHour(int hour) {
		this.hour = hour;
	}


	public int getMinute() {
		return minute;
	}


	public void setMinute(int minute) {
		this.minute = minute;
	}

	
	
}
