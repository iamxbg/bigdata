//package bigdata;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Map;
//
//import org.bson.BSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sequoiadb.exception.BaseException;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.service.sequoia.BobcatService;
//import bigdata.util.BobcatRecursiveTask;
//import bigdata.util.EChartUtil;
//import bigdata.util.TimeRange;
//import bigdata.util.TimeRangeUnit;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class EChartUtilTest {
//
//	@Autowired
//	BobcatService bobcatService;
//	
//	//@Test
//	public void testErrOfDayGroupByHour() throws BaseException, InterruptedException{
//		String startTime="2016-11-28 00:00:00";
//		String endTime="2016-11-29 00:00:00";
//		Map<Object, Long> map=bobcatService.countByHour(startTime, endTime);
//		BSONObject converted=EChartUtil.converTo_DaySliceByHour(map);
//		System.out.println("converted:"+converted);
//		
//	}
//	
//	//@Test
//	public void testErrOfYearGroupByMonth() throws BaseException,InterruptedException{
//		String startTime="2016-01-01 00:00:00";
//		String endTime="2017-01-01 00:00:00";
//		
//		System.out.println(EChartUtil.covertTo_YearSliceByMonth(bobcatService.countByMonth(startTime, endTime)));
//		
//	}
//	
//	//@Test
//	public void testErrOfMonthGroupByDay() throws BaseException,InterruptedException, ParseException{
//		String startTime="2016-11-01 00:00:00";
//		String endTime="2016-12-01 00:00:00";
//		
//		Map<Object, Long> map=bobcatService.countByDay(startTime, endTime);
//		System.out.println("map:"+map);
//		System.out.println(EChartUtil.convertTo_MonthSliceByDay(startTime, endTime, map));
//	}
//	
//	//@Test
//	public void testErrGroupByErrMessage() throws BaseException, InterruptedException{
//		String startTime="2016-02-01 00:00:00";
//		String endTime="2016-03-01 00:00:00";
//		
//		System.out.println(EChartUtil.convertTo_ErrMessage(bobcatService.countByErrMessage(startTime, endTime)));
//	}
//	
//	//@Test
//	public void testErrGroupByHostname() throws BaseException, InterruptedException{
//		String startTime="2016-10-01 00:00:00";
//		String endTime="2016-11-01 00:00:00";
//		
//		System.out.println(EChartUtil.convertTo_Hostname(bobcatService.countByHostname(startTime, endTime)));
//	}
//	
//	//@Test
//	public void testErrChartOfTimeGroupByHostname() throws BaseException, InterruptedException{
//
//			TimeRange monthRange=TimeRange.createBy_Month_Day(2016, 10);
//			System.out.println("startTime:"+monthRange.getStartTimeStr()+" ~ endTime:"+monthRange.getEndTimeStr());
//			Map<Object, Map<Object,Long>> map= bobcatService.generateTimeChartGroupByHostname(monthRange.getStartTimeStr(),monthRange.getEndTimeStr(), TimeRangeUnit.DAY);
//			System.out.println("map<>>>>>>>>>>>:"+map);
//			System.out.println("##########"+EChartUtil.convertTo_TimeChartGroupByHostname(monthRange,map));
//	}
//	
//	//@Test
//	public void testErrChartOfTimeGroupByErrMessage() throws BaseException, InterruptedException{
//		//-- TimeRange range=TimeRange.createBy_Month_Day(2016, 10);
//		TimeRange range=TimeRange.createBy_Date_Hour(2016, 10, 1);
//		//TimeRange range=TimeRange.createBy_Year_Month(2016);
//		System.out.println("startTime:"+range.getStartTimeStr()+" ~ endTime:"+range.getEndTimeStr());
//		Map<Object, Map<Object,Long>> map= bobcatService.generateTimeChartGroupByErrMessage(range.getStartTimeStr(),range.getEndTimeStr(), range.getTimeFragment());
//		System.out.println("map<>>>>>>>>>>>:"+map);
//		System.out.println("##########"+EChartUtil.convertTo_TimeChartGroupByHostname(range,map));
//	}
//	
//	@Test
//	public void testErrCountLastOneHourGroupByMinute() throws BaseException, InterruptedException{
//		
//		Calendar endCal=Calendar.getInstance();
//			endCal.set(Calendar.YEAR, 2016);
//			endCal.set(Calendar.MONTH, 10);
//			endCal.set(Calendar.DAY_OF_MONTH, 28);
//			endCal.set(Calendar.MINUTE,0);
//			endCal.set(Calendar.SECOND, 0);
//			endCal.set(Calendar.HOUR_OF_DAY, 11);
//		Calendar startCal=Calendar.getInstance();
//			startCal.setTime(endCal.getTime());
//			startCal.add(Calendar.HOUR_OF_DAY, -1);
//			
//			String startTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startCal.getTime());
//			String endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endCal.getTime());
//				
//			
//		BSONObject converted=EChartUtil.convertTo_LastOneHourSliceByMinute(endCal.getTime(),bobcatService.countLasOneHourGroupByMinute(startTime, endTime));
//		System.out.println("converted::"+converted);
//	
//	}
//	
//}
