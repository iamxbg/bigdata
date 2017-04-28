//package bigdata.quartz;
//
//import org.bson.BSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class Bobcat_LogConvertorTest {
//
//	
//	
//	//@Test
//	public void testStatistics(){
//		
//		String startTime="2016-10-10 00:00:00";
//		String endTime="2016-10-10 10:00:00";
//		Bobcat_LogConvertor convertor=new Bobcat_LogConvertor(startTime, endTime);
//		BSONObject result=convertor.statistics();
//		
//		System.out.println(result);
//	}
//	
//	
//	@Test
//	public void testDoStatistics(){
//
//		String startTime="2016-10-10 00:00:00";
//		String endTime="2016-10-10 01:00:00";
//		Bobcat_LogConvertor convertor=new Bobcat_LogConvertor(startTime, endTime);
//		
//		convertor.doStatistics();
//	}
//}
