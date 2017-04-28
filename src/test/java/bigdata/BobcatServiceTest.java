//package bigdata;
//
//import static java.util.stream.Collectors.groupingBy;
//import static java.util.stream.Collectors.summingLong;
//
//import java.util.Map;
//import java.util.stream.Stream;
//
//import org.bson.BSONObject;
//import org.bson.BasicBSONObject;
//import org.bson.types.BasicBSONList;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sequoiadb.base.DBCursor;
//import com.sequoiadb.base.Sequoiadb;
//import com.sequoiadb.exception.BaseException;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.service.sequoia.BobcatService;
//import bigdata.util.EChartUtil;
//import bigdata.util.TimeRangeUnit;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class BobcatServiceTest {
//
//	@Autowired
//	private BobcatService bobcatService;
//	
//	public Map<Object, Long> countByErrMessage(String startTime,String endTime) throws BaseException,InterruptedException{
//		
//		return null;
//	};
//	
//	public Map<Object, Long> countByHostname(String startTime,String endTime) throws BaseException,InterruptedException{
//		return null;
//	};
//	
//	//@Test
//	public void testCountByHour() throws BaseException,InterruptedException{
//
//		String startTime="2016-11-28 00:00:00";
//		String endTime="2016-11-29 00:00:00";
//		
//		Map<Object, Long> countMap=bobcatService.countByHour(startTime, endTime);
//		
//		System.out.println("countMap::"+countMap);
//		
//		
//		
//	};
//	
//	@Test
//	public void countByDay() throws BaseException,InterruptedException{
//		String startTime="2016-12-06 00:00:00";
//		String endTime="2016-12-08 00:00:00";
//		Map<Object, Long> map=bobcatService.countByDay(startTime, endTime);
//		System.out.println("Map:"+map);
//	};
//	
//	public Map<Object, Long> countByMonth(String startTime,String endTime) throws BaseException,InterruptedException{
//		return null;
//	};
//	
////	@Test
//	public void generateTimeChartGroupByErrMessage() throws BaseException,InterruptedException{
//		String startTime="2016-11-01 00:00:00";
//		String endTime="2016-11-02 00:00:00";
//		TimeRangeUnit timeDust=TimeRangeUnit.HOUR;
//		System.out.println(bobcatService.generateTimeChartGroupByErrMessage(startTime, endTime, timeDust));
//	};
//	
//	public Map<Object, Map<Object,Long>> generateTimeChartGroupByHostname(String startTime,String endTime,TimeRangeUnit timeDust) throws BaseException,InterruptedException{
//		return null;
//	};
//	
//	//@Test
//	public void getErrCounfOfLastOneHourGroupByMinute() throws BaseException, InterruptedException{
//		String startTime="2016-11-28 07:00:00";
//		String endTime="2016-11-28 08:00:00";
//		Map<Object, Long> map=bobcatService.countLasOneHourGroupByMinute(startTime, endTime);
//		System.out.println("map:"+map);
//	}
//	//@Test
//	public void getErrCountOfLastOneDayGroupByMinute()throws BaseException,InterruptedException{
//		String startTime="2016-11-25 07:00:00";
//		String endTime="2016-11-29 08:00:00";
//		Map<Object, Long> map=bobcatService.countLasOneHourGroupByMinute(startTime, endTime);
//		System.out.println("map:"+map);
//	}
//	
//	
//	
//
//}
//
