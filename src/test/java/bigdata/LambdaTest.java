//package bigdata;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.LongSummaryStatistics;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//import java.util.stream.LongStream;
//import java.util.stream.Stream;
//import java.util.stream.Stream.Builder;
//
//import javax.annotation.Resource;
//
//import org.apache.logging.log4j.core.layout.LockingStringBuilderEncoder;
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
//import com.sequoiadb.base.SequoiadbDatasource;
//import com.sequoiadb.exception.BaseException;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.util.D3Util;
//
//import static java.util.stream.Collectors.*;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class LambdaTest {
//
//	@Resource(name="sequoiadbDatasource")
//	private SequoiadbDatasource ds;
//	
//	@Resource(name="bigdataMiddleDS")
//	private SequoiadbDatasource ds2;
//	
//	//@Test
//	public void testGetContGroupHostname() throws BaseException, InterruptedException{
//		System.out.println("runt");	
//		//group by hostname
//		
//		Sequoiadb sdb=ds2.getConnection();
//		
//		String startTime="2016-12-28 00:00:00";
//		String endTime="2016-12-23 00:00:00";
//
//		
//		String sql=new StringBuilder("select time,errs_hostname from bigdata.bobcat where time>='")
//					.append(startTime)
//					.append("' and time<='")
//					.append(endTime)
//					.append("'").toString();
//
//		DBCursor cur=sdb.exec(sql);
//		
//		Stream<Object> s=Stream.empty();
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//			System.out.println("cur: >time:"+r.get("time")+" errs_hostname:"+r.get("errs_hostname"));
//			//((BasicBSONList)cur.getCurrent().get("errs_hostname")).stream()
//			if(r.get("errs_hostname")!=null){
//				Stream<Object> s2=((BasicBSONList)r.get("errs_hostname")).stream();
//				s=Stream.concat(s,s2);
//			}
//			//b.add((BasicBSONList) cur.getCurrent().get("errs_hostname"));
//		}
//		
//		Map<Object, Long> map=s.parallel().filter(r->{System.out.println("filter:"+r.toString());return ((BSONObject)r).get("hostname")!=null;}).collect(Collectors.groupingBy(r ->((BSONObject)r).get("hostname"),summingLong(r->(Long)((BSONObject)r).get("count"))));
//		
//		System.out.println("map::"+map);
//	}
//	
//	
//	//@Test
//	public void testGetCountGroupByErrMessage() throws BaseException, InterruptedException{
//		
//		System.out.println("runt");	
//		//group by hostname
//		
//		Sequoiadb sdb=ds2.getConnection();
//		
//		String startTime="2016-10-01 00:00:00";
//		String endTime="2016-11-01 00:00:00";
//
//		
//		String sql=new StringBuilder("select time,errs_errMessage from bigdata.bobcat where time>='")
//					.append(startTime)
//					.append("' and time<='")
//					.append(endTime)
//					.append("'").toString();
//
//		DBCursor cur=sdb.exec(sql);
//		
//		Stream<Object> s=Stream.empty();
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//			System.out.println("cur: >time:"+r.get("time")+" errs_errMessage:"+r.get("errs_errMessage"));
//			//((BasicBSONList)cur.getCurrent().get("errs_hostname")).stream()
//			if(r.get("errs_errMessage")!=null){
//				Stream<Object> s2=((BasicBSONList)r.get("errs_errMessage")).stream();
//				s=Stream.concat(s,s2);
//			}
//			//b.add((BasicBSONList) cur.getCurrent().get("errs_hostname"));
//		}
//		
//		Map<Object, Long> map=s.parallel().filter(r->{System.out.println("filter:"+r.toString());return ((BSONObject)r).get("ErrMessage")!=null;}).collect(Collectors.groupingBy(r ->((BSONObject)r).get("ErrMessage"),summingLong(r->(Long)((BSONObject)r).get("count"))));
//		
//		System.out.println("map::"+map);
//		
//	}
//	
//	//@Test
//	public void testCountingByTimeRange() throws BaseException, InterruptedException{
//		
//		Sequoiadb sdb=ds2.getConnection();
//		
//		String startTime="2016-04-01 00:00:00";
//		String endTime="2016-05-15 00:00:00";
//		
//		String sql=new StringBuilder("select time,count from bigdata.bobcat where time>='")
//				.append(startTime)
//				.append("' and time<='")
//				.append(endTime)
//				.append("'").toString();
//		
//		DBCursor cur=sdb.exec(sql);
//		
//		List<Long> list=new ArrayList<>();
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//			list.add((Long)r.get("count"));
//		}
//		
//	//	LongStream s=(LongStream)list.stream();
//		Stream<Long> s=list.stream();
//		
//		Long count=s.reduce(0l, (x,y) -> x+y);
//		
//		System.out.println(count);
//	}
//	
//	//@Test
//	public void testGetLastOneHourErrMessageGroupByTenSeconds() throws BaseException, InterruptedException{
//		Sequoiadb sdb=ds.getConnection();
//		
//		String startTime="2016-11-28 08:00:00";
//		String endTime="2016-11-28 09:00:00";
//		
//		String sql=new StringBuilder("select * from cdbbc.dcslog where log_date >='"+startTime+"' and log_date <'"+endTime+"'").toString();
//		
//		DBCursor cur=sdb.exec(sql);
//		
//		Stream<Object> s=Stream.empty();
//		
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//			s=Stream.concat(s, Stream.of(r));
//		}
//		
//		
//		Map<Object, Long> map=s.parallel().collect(groupingBy(r ->((BSONObject)r).get("log_date").toString().subSequence(0, 16),counting()));
//		
//		sdb.disconnect();
//		
//		System.out.println("map::"+map);
//	}
//	
////	@Test
//	public void testCountingErrMessageGroupByDay() throws BaseException, InterruptedException{
//		System.out.println("runt");	
//		//group by hostname
//		
//		
//		Sequoiadb sdb=ds2.getConnection();
//		
//		String startTime="2016-10-04 00:00:00";
//		String endTime="2016-10-05 00:00:00";
//
//		
//		String sql=new StringBuilder("select time,errs from bigdata.bobcat where time>='")
//					.append(startTime)
//					.append("' and time<'")
//					.append(endTime)
//					.append("'").toString();
//
//		DBCursor cur=sdb.exec(sql);
//		
//		
//		Stream<Object> s=Stream.empty();
//
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//			if(r.get("errs")!=null){
//				Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
//					mapped.put("hostname", ((BSONObject)k).get("hostname"));
//					mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
//					mapped.put("count", ((BSONObject)k).get("count"));
//					return mapped;
//				});
//				s=Stream.concat(s, s1);
//			}		
//		}
//		
//		/*
//		Map<Object, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//				.collect(groupingBy(r->((BSONObject)r).get("time").toString().substring(0, 13),summingLong(r->(Long)((BSONObject)r).get("count"))));
//		*/
//		
//		// err count group by day
//		/*
//		Map<Object, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//					.collect(groupingBy(r->((BSONObject)r).get("time").toString().substring(0, 10),summingLong(r->(Long)((BSONObject)r).get("count"))));
//		*/
//		
//		// generate Map<[date(yyyy-MM-dd)], Map<ErrMessage,Long>>
//		/*
//		Map<Object, Map<Object,Long>> map=s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
//							.collect(groupingBy(r-> ((BSONObject)r).get("time").toString().substring(0, 10), groupingBy(k->((BSONObject)k).get("ErrMessage") , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
//		*/
//		
//		// generate Map<[date(yyyy-MM-dd)],Map<[hostname],Long>>
//		/*
//		Map<Object, Map<Object,Long>> map=s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
//				.collect(groupingBy(r-> ((BSONObject)r).get("time").toString().substring(0, 10), groupingBy(k->((BSONObject)k).get("hostname") , summingLong( k-> (Long)((BSONObject)k).get("count") ))));*/
//		
//		// generate Map<Object,Long> count group by ErrMessage
//		/*
//		Map<Object, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//				.collect(groupingBy(r->((BSONObject)r).get("ErrMessage").toString().substring(0, 10),summingLong(r->(Long)((BSONObject)r).get("count"))));
//		*/
//		
//		// generate Map<Object,Long> count  group by hostname
//		/*
//		Map<Object, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//				.collect(groupingBy(r->((BSONObject)r).get("hostname"),summingLong(r->(Long)((BSONObject)r).get("count"))));*/
//		
//		/*
//		Map<Object, Map<Object,Long>> map=s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
//				.collect(groupingBy(r-> ((BSONObject)r).get("hostname"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0, 10) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
//		*/
//		
//		// System.out.println("map::"+map);
//		
//		
//	}
//	
//	
//	@Test
//	public void testSankeyFromHostnameToErrMessageType() throws BaseException, InterruptedException{
//		Sequoiadb sdb=ds2.getConnection();
//		
//		String startTime="2016-12-06 00:00:00";
//		String endTime="2016-12-07 00:00:00";
//		String sql=new StringBuilder("select * from bigdata.bobcat where time>='")
//				.append(startTime)
//				.append("' and time<'")
//				.append(endTime).append("'").toString();
//		
//		DBCursor cur=sdb.exec(sql);
//		Stream<BSONObject> s=Stream.empty();
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//				if(r.get("errs")!=null){
//					Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject();
//						mapped.put("hostname", ((BSONObject)k).get("hostname"));
//						mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
//						mapped.put("count", ((BSONObject)k).get("count"));
//						return mapped;
//					});
//					s=Stream.concat(s, s1);
//				}	
//				
//			}
//			/*
//			Map<BSONObject, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//				.collect(Collectors.groupingBy(r-> {
//					BSONObject result=new BasicBSONObject();
//						result.put("hostname", ((BSONObject)r).get("hostname"));
//						result.put("ErrMessage", ((BSONObject)r).get("ErrMessage"));
//						return result;
//						},summingLong( r-> (Long)((BSONObject)r).get("count") )));
//		
//		*/
//		Map<String, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//				.collect(Collectors.groupingBy(r-> {
//
//						return ((BSONObject)r).get("hostname").toString()+"@"+((BSONObject)r).get("ErrMessage").toString();
//						},summingLong( r-> (Long)((BSONObject)r).get("count") )));
//						
//			System.out.println("map:"+map);
//			BSONObject chart=D3Util.convertToSankeyChart(map);
//			System.out.println("d3:"+chart.toString());
//		}
//		
//	
//	//@Test
//	public void testSankeyFromErrMessageTypeToHostname() throws BaseException, InterruptedException{
//		Sequoiadb sdb=ds2.getConnection();
//		
//		String startTime="2016-12-06 00:00:00";
//		String endTime="2016-12-07 00:00:00";
//		String sql=new StringBuilder("select * from bigdata.bobcat where time>='")
//				.append(startTime)
//				.append("' and time<'")
//				.append(endTime).append("'").toString();
//		
//		DBCursor cur=sdb.exec(sql);
//		Stream<BSONObject> s=Stream.empty();
//		while(cur.hasNext()){
//			BSONObject r=cur.getNext();
//				if(r.get("errs")!=null){
//					Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject();
//						mapped.put("hostname", ((BSONObject)k).get("hostname"));
//						mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
//						mapped.put("count", ((BSONObject)k).get("count"));
//						return mapped;
//					});
//					s=Stream.concat(s, s1);
//				}	
//				
//			}
//			
//			Map<BSONObject, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//				.collect(Collectors.groupingBy(r-> {
//					BSONObject result=new BasicBSONObject();
//						result.put("hostname", ((BSONObject)r).get("hostname"));
//						result.put("ErrMessage", ((BSONObject)r).get("ErrMessage"));
//						return result;
//						},summingLong( r-> (Long)((BSONObject)r).get("count") )));
//			
//			System.out.println("map:"+map);
//			
//
//		}
//		
//	
//}
