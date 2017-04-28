package bigdata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.sequoiadb.util.logger;

public class EChartUtil {

	private static final Logger logger=LogManager.getLogger();
	
	
	public static BSONObject converTo_DaySliceByHour(Map<Object, Long> map){
		BSONObject converted=new BasicBSONObject();
			
		BasicBSONList data=new BasicBSONList();

		BasicBSONList labels=new BasicBSONList();
		for(int i=0;i<24;i++){
			labels.add(i);
			boolean found=false;
			for(Object key:map.keySet()){
				String subKey=key.toString().substring(11, 13);
				//System.out.println("SUB-KEY:"+subKey);
				int hour=subKey.charAt(0)=='0'?Integer.parseInt(subKey.substring(1)):Integer.parseInt(subKey);	
				if(i==hour){
					data.add(map.get(key));
					found=true;
					break;
				}
			}
			if(!found) data.add(0);
		}
		
		converted.put("xAxisLen", 24);
		converted.put("xAxisLabels", labels);
		converted.put("data", data);

		return converted;
	};
	
	public static BSONObject covertTo_YearSliceByMonth(Map<Object, Long> map){

				BSONObject converted=new BasicBSONObject();
				
				BasicBSONList labels=new BasicBSONList();
				BasicBSONList data=new BasicBSONList();
				for(int i=1;i<=12;i++){
					labels.add(i);
					boolean found=false;
					for(Object key:map.keySet()){
						String subKey=key.toString().substring(5, 7);
						System.out.println("month:"+subKey);
						int month=subKey.charAt(0)=='0'?Integer.parseInt(subKey.substring(1)):Integer.parseInt(subKey);	
						if(i==month){
							data.add(map.get(key));
							found=true;
							break;
						}
					}
					if(!found) data.add(0);
				}
				
				converted.put("xAxisLen", 12);
				converted.put("xAxisLabels",labels);
				converted.put("data", data);
				
		return converted;
	}
	
	public static BSONObject convertTo_MonthSliceByDay(String startTime,String endTime,Map<Object, Long> map) throws ParseException{
		Date startDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
		Date endDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
		
		
		Calendar startCal=Calendar.getInstance();
				startCal.setTime(startDate);
		Calendar endCal=Calendar.getInstance();
				endCal.setTime(endDate);
			
			int dayOfMonth=(int) ((endCal.getTimeInMillis()-startCal.getTimeInMillis())/(1000*24*60*60));

			
			System.out.println("dayOfMonth:::"+dayOfMonth);
			
			BSONObject converted=new BasicBSONObject();
			BasicBSONList data=new BasicBSONList();
			BasicBSONList labels=new BasicBSONList();
			
			for(int i=1;i<=dayOfMonth;i++){
				labels.add(i);
				boolean found=false;
				for(Object key:map.keySet()){
					String subKey=key.toString().substring(8, 10);	
					int day=subKey.charAt(0)=='0'?Integer.parseInt(subKey.substring(1)):Integer.parseInt(subKey);
					if(i==day){
						data.add(map.get(key));
						found=true;
						break;
					}
					
					
				}
				if(!found) data.add(0);
				
			}
			converted.put("data", data);
			converted.put("xAxisLen", dayOfMonth);
			converted.put("xAxisLabels", labels);
		return converted;
	}
	
	public static BSONObject convertTo_LastOneHourSliceByMinute(Date now,Map<Object,Long> map){
		
		Calendar endCal=Calendar.getInstance();
			endCal.setTime(now);
		Calendar startCal=Calendar.getInstance();
			startCal.setTime(now);
			startCal.add(Calendar.HOUR_OF_DAY, -1);
		
		BSONObject converted=new BasicBSONObject();
		
		BasicBSONList data=new BasicBSONList();
		
		while(startCal.getTimeInMillis()<endCal.getTimeInMillis()){
			String subDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startCal.getTime()).substring(0, 16);
			boolean found=false;
			for(Object key:map.keySet()){
				if(subDate.toString().equals(key)){
					data.add(map.get(key));
					found=true;
					break;
				}
			}
			if(!found) data.add(0);
			startCal.add(Calendar.MINUTE, 1);
		}
		
		converted.put("data", data);
		
		return converted;
	}
	
	public static BSONObject convertTo_ErrMessage(Map<Object, Long> map){
		
		BSONObject converted=new BasicBSONObject();
		BasicBSONList data=new BasicBSONList();
		BasicBSONList labels=new BasicBSONList();
		for(Object key:map.keySet()){
			labels.add(key.toString());
			data.add(map.get(key));
		}
		converted.put("data", data);
		converted.put("xAxisLabels", labels);
		converted.put("xAxisLen", map.keySet().size());
		
		return converted;
	}
	
	public static BSONObject convertTo_Hostname(Map<Object, Long> map){
		BSONObject converted=new BasicBSONObject();
		BasicBSONList data=new BasicBSONList();
		BasicBSONList labels=new BasicBSONList();
		for(Object key:map.keySet()){
			labels.add(key.toString());
			data.add(map.get(key));
		}
		converted.put("data", data);
		converted.put("xAxisLabels", labels);
		converted.put("xAxisLen", map.keySet().size());
		
		return converted;
	}
	
	
	public static BSONObject convertTo_TimeChartGroupByHostname(TimeRange range,Map<Object, Map<Object, Long>> map){
			BSONObject converted=new BasicBSONObject();
			BasicBSONList labels=new BasicBSONList();
			BasicBSONList legends=new BasicBSONList();
			
			List<TimeSlice> timeSliceList=DateUtil.getTimeSliceListByTimeRange(range);
			System.out.println("@TimeSLiceLEn:"+timeSliceList.size());
			
			int timeSliceStrLen=0;
	
			if(range.getTimeWidth()==TimeRangeUnit.YEAR && range.getTimeFragment()==TimeRangeUnit.MONTH){
				timeSliceStrLen=7;
				
				
			}else if(range.getTimeWidth()==TimeRangeUnit.MONTH && range.getTimeFragment()==TimeRangeUnit.DAY){
				timeSliceStrLen=10;
			}else if(range.getTimeWidth()==TimeRangeUnit.DAY && range.getTimeFragment()==TimeRangeUnit.HOUR){
				timeSliceStrLen=13;
			}
			
			BSONObject seriesMap=new BasicBSONObject();
			for(Object key:map.keySet()){
				legends.add(key);
				BasicBSONList data=new BasicBSONList();
				Map<Object, Long> subMap=map.get(key);
				for(int i=0;i<timeSliceList.size();i++){
					String timeSliceStr=timeSliceList.get(i).getStartTimeStr().substring(0,timeSliceStrLen);
					
					if(subMap.containsKey(timeSliceStr)) data.add(subMap.get(timeSliceStr));
					else data.add( 0l);
				}	
				seriesMap.put(key.toString(), data);
			}
			//load labels
			for(int i=0;i<timeSliceList.size();i++){
				String timeSubStr=timeSliceList.get(i).getStartTimeStr().substring(timeSliceStrLen-2, timeSliceStrLen);
				labels.add(timeSubStr.charAt(0)=='0'?timeSubStr.substring(1):timeSubStr);
			}
			
			converted.put("seriesMap", seriesMap);
			converted.put("xAxisLabels", labels);
			converted.put("xAxisLen", timeSliceStrLen);
			converted.put("legends", legends);
		return converted;
	}
	
	public static BSONObject convertTo_TimeChartGroupByErrMessage(TimeRange range,Map<Object, Map<Object, Long>> map){
		BSONObject converted=new BasicBSONObject();
		BasicBSONList labels=new BasicBSONList();
		BasicBSONList legends=new BasicBSONList();
		
		List<TimeSlice> timeSliceList=DateUtil.getTimeSliceListByTimeRange(range);
		System.out.println("@TimeSLiceLEn:"+timeSliceList.size());
		
		int timeSliceStrLen=0;
		if(range.getTimeWidth()==TimeRangeUnit.YEAR && range.getTimeFragment()==TimeRangeUnit.MONTH){
			timeSliceStrLen=7;
		}else if(range.getTimeWidth()==TimeRangeUnit.MONTH && range.getTimeFragment()==TimeRangeUnit.DAY){
			timeSliceStrLen=10;
		}else if(range.getTimeWidth()==TimeRangeUnit.DAY && range.getTimeFragment()==TimeRangeUnit.HOUR){
			timeSliceStrLen=13;
		}else if(range.getTimeWidth()==TimeRangeUnit.HOUR && range.getTimeFragment()==TimeRangeUnit.MINUTE){
			timeSliceStrLen=16;
		}
		
		BSONObject seriesMap=new BasicBSONObject();
		for(Object key:map.keySet()){
			legends.add(key);
			BasicBSONList data=new BasicBSONList();
			Map<Object, Long> subMap=map.get(key);
			for(int i=0;i<timeSliceList.size();i++){
				String timeSliceStr=timeSliceList.get(i).getStartTimeStr().substring(0,timeSliceStrLen);
				
				if(subMap.containsKey(timeSliceStr)) data.add(subMap.get(timeSliceStr));
				else data.add( 0l);
			}	
			seriesMap.put(key.toString(), data);
		}
		//load labels
		for(int i=0;i<timeSliceList.size();i++){
			String timeSubStr=timeSliceList.get(i).getStartTimeStr().substring(timeSliceStrLen-2, timeSliceStrLen);
			labels.add(timeSubStr.charAt(0)=='0'?timeSubStr.substring(1):timeSubStr);
		}
		
		converted.put("seriesMap", seriesMap);
		converted.put("xAxisLabels", labels);
		converted.put("xAxisLen", timeSliceStrLen);
		converted.put("legends", legends);
	return converted;
	}
	
	
	
	
}
