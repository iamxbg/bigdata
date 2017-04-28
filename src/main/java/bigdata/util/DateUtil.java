package bigdata.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	private static SimpleDateFormat defaultDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	
	private static List<TimeSlice> generateTimeRange(List<Date> times){
		List<TimeSlice> list=new ArrayList<>();
		for(int i=0;i<times.size()-1;i++){	
			TimeSlice range=new TimeSlice(times.get(i),times.get(i+1));
			list.add(range);
		}
		return list;
	}
	
	
	/**
	 * Convert Start date, End date to List of TimeSlice.
	 * 
	 * @param startTime
	 * @param endTime 
	 * @param slice 
	 * @return
	 */
	public static List<TimeSlice> ConvertToTimeSliceList(Date startTime,Date endTime,TimeRangeUnit slice){
		

		
		Calendar startCal=Calendar.getInstance();
				startCal.setTime(startTime);
		Calendar endCal=Calendar.getInstance();
				endCal.setTime(endTime);

		List<Date> timePoints=new ArrayList<>();
		timePoints.add(startTime);
		
		boolean first=true;
		while(true){
			if(first){
				switch(slice){
					case YEAR:{
							startCal.set(Calendar.MONTH, 0);
							startCal.set(Calendar.DAY_OF_MONTH, 0);
							startCal.set(Calendar.HOUR_OF_DAY, 0);
							startCal.set(Calendar.MINUTE, 0);
							startCal.set(Calendar.SECOND, 0);};break;
					case MONTH:{
							startCal.set(Calendar.DAY_OF_MONTH, 1); //the first day of month is 1.
							startCal.set(Calendar.HOUR_OF_DAY, 0);
							startCal.set(Calendar.MINUTE, 0);
							startCal.set(Calendar.SECOND, 0);};break;
					case WEEK:{	
							startCal.setFirstDayOfWeek(Calendar.MONDAY);
							startCal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
							startCal.set(Calendar.HOUR_OF_DAY, 0);
							startCal.set(Calendar.MINUTE, 0);
							startCal.set(Calendar.SECOND, 0);
							};break;
					case DAY:{
							startCal.set(Calendar.HOUR_OF_DAY, 0);
							startCal.set(Calendar.MINUTE, 0);
							startCal.set(Calendar.SECOND, 0);}break;
					case HOUR:{
							startCal.set(Calendar.MINUTE, 0);
							startCal.set(Calendar.SECOND, 0);};break;
					case MINUTE:{
							startCal.set(Calendar.SECOND, 0);};break;
					default: break;		
				}
			}	first=false;

			
				switch(slice){
				case YEAR:startCal.add(Calendar.YEAR, 1);;break;
				case MONTH:{
					int maxDayOfMonth=startCal.getActualMaximum(Calendar.DAY_OF_MONTH);
					startCal.add(Calendar.DATE,maxDayOfMonth);		
				}break;
				case WEEK:startCal.add(Calendar.WEEK_OF_YEAR, 1);break;
				case DAY:startCal.add(Calendar.DAY_OF_MONTH, 1);break;
				case HOUR:startCal.add(Calendar.HOUR_OF_DAY, 1);break;
				case MINUTE:startCal.add(Calendar.MINUTE, 1);break;
				default :break;
				}
			
			if(startCal.before(endCal)){
				timePoints.add(startCal.getTime());
			}else{
				timePoints.add(endCal.getTime());
				break;
			}
			
			
		}
		
		return generateTimeRange(timePoints);
	}
	
	/**
	 * 将时间的MilliSecond转换为特定形式的字符串
	 * @param range
	 * @param sdf
	 * @return 
	 */
	public static Map<String, String> parseTimeMilliToStr(TimeSlice range,SimpleDateFormat sdf){
		Map<String, String> timeRangeMap=new HashMap<>();
			Date start=range.getStart();
			Date end=range.getEnd();
			timeRangeMap.put("startTime", sdf.format(start));
			timeRangeMap.put("endTime", sdf.format(end));
			return timeRangeMap;
	}
	
	public static Map<String, String> parseTimeMilliToStr(TimeSlice range){
		Map<String, String> timeRangeMap=new HashMap<>();
			Date start=range.getStart();
			Date end=range.getEnd();
			timeRangeMap.put("startTime", defaultDateFormat.format(start));
			timeRangeMap.put("endTime", defaultDateFormat.format(end));
			return timeRangeMap;
	}
	
	

	
	private static List<TimeSlice> getTimeSliceListOfMonthSliceByDay(int year,int month,int day,int hour,int minute){
		Calendar startTime=Calendar.getInstance();
				startTime.set(year, month, 1, 0, 0, 0);
				startTime.set(Calendar.MILLISECOND, 0);
		Calendar endTime=Calendar.getInstance();
				endTime.set(year, month, 1, 0, 0, 0);
				endTime.set(Calendar.MILLISECOND, 0);
				int dayOfMonth=endTime.getActualMaximum(Calendar.DAY_OF_MONTH);
				endTime.add(Calendar.DAY_OF_MONTH, dayOfMonth);
				
		return ConvertToTimeSliceList(startTime.getTime(), endTime.getTime(), TimeRangeUnit.DAY);
	}
	
	private static List<TimeSlice> getTimeSliceListOfYearSliceByMonth(int year,int month,int day,int hour,int minute){
		Calendar startTime=Calendar.getInstance();
			startTime.set(Calendar.YEAR, year);
			startTime.set(Calendar.DAY_OF_YEAR,1);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			startTime.set(Calendar.MILLISECOND, 0);
			
		Calendar endTime=Calendar.getInstance();
			endTime.set(Calendar.YEAR, year+1);
			endTime.set(Calendar.DAY_OF_YEAR, 0);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			startTime.set(Calendar.MILLISECOND, 0);
			endTime.add(Calendar.DAY_OF_YEAR, 0);
			
			
		return ConvertToTimeSliceList(startTime.getTime(), endTime.getTime(), TimeRangeUnit.MONTH);
	}
	
	
	private static List<TimeSlice> getTimeSliceListOfYearSliceByDay(int year,int month,int day,int hour,int minute){
		Calendar startTime=Calendar.getInstance();
			startTime.set(Calendar.YEAR, year);
			startTime.set(Calendar.DAY_OF_YEAR,0);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			startTime.set(Calendar.MILLISECOND, 0);
			
		Calendar endTime=Calendar.getInstance();
			endTime.set(Calendar.YEAR, year+1);
			endTime.set(Calendar.DAY_OF_YEAR, 0);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			startTime.set(Calendar.MILLISECOND, 0);
			endTime.add(Calendar.DAY_OF_YEAR, -1);
			
			
			
		return ConvertToTimeSliceList(startTime.getTime(), endTime.getTime(), TimeRangeUnit.DAY);
	}
	

	

	
	
	
	 public static List<TimeSlice> getTimeSliceListOfDateSliceByHour(int year,int month,int day,int hour,int minute){
		Calendar startTime=Calendar.getInstance();
				startTime.set(year, month, day,0,0,0);
				startTime.set(Calendar.MILLISECOND, 0);
		Calendar endTime=Calendar.getInstance();
				endTime.set(year, month, day,0,0,0);
				endTime.set(Calendar.MILLISECOND, 0);
					endTime.add(Calendar.DATE, 1);
					
		return ConvertToTimeSliceList(startTime.getTime(), endTime.getTime(), TimeRangeUnit.HOUR);
		
	}
	 

	 
	 private static List<TimeSlice> getTimeSliceListOfHourSliceByMinute(int year,int month,int day,int hour,int minute){
		 Calendar startTime=Calendar.getInstance();
		 			startTime.set(year, month, day, hour, 0,0);
		 Calendar endTime=Calendar.getInstance();
		 			endTime.set(year, month, day, hour, 0, 0);
		 			endTime.add(Calendar.HOUR_OF_DAY, 1);
		 			
		 return ConvertToTimeSliceList(startTime.getTime(), endTime.getTime(), TimeRangeUnit.MINUTE);
		 			
	 }
	 
	 
	 public static List<TimeSlice> getTimeSliceListByTimeRange(TimeRange range){
		 
		 TimeRangeUnit width=range.getTimeWidth();
		 TimeRangeUnit fragment=range.getTimeFragment();
		 
		 int year=range.getYear();
		 int month=range.getMonth();
		 int day=range.getDay();
		 int hour=range.getHour();
		 int minute=range.getMinute();
		 
		 if(width==TimeRangeUnit.YEAR){
			 if(fragment==TimeRangeUnit.MONTH) return getTimeSliceListOfYearSliceByMonth(year, month, day, hour, minute);
			 if(fragment==TimeRangeUnit.DAY) return getTimeSliceListOfYearSliceByDay(year, month, day, hour, minute);
		 }else if(width==TimeRangeUnit.MONTH){
			  if(fragment==TimeRangeUnit.DAY) return getTimeSliceListOfMonthSliceByDay(year, month,  day, hour, minute);
		 }else if(width==TimeRangeUnit.DAY){
			 if(fragment==TimeRangeUnit.HOUR) return getTimeSliceListOfDateSliceByHour(year, month, day, hour, minute);
		 }else if(width==TimeRangeUnit.HOUR){
			 if(fragment==TimeRangeUnit.MINUTE) return getTimeSliceListOfHourSliceByMinute(year, month, day, hour, minute);
		 }

		 return null;
	 }
	
	
}
