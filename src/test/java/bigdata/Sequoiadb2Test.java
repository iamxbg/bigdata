//package bigdata;
//
//import static java.util.stream.Collectors.groupingBy;
//import static java.util.stream.Collectors.summingLong;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Stream;
//import java.util.stream.Stream.Builder;
//
//import javax.annotation.Resource;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.bson.BSONObject;
//import org.bson.BasicBSONObject;
//import org.bson.types.BSONTimestamp;
//import org.bson.types.BasicBSONList;
//import org.bson.types.ObjectId;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.sequoiadb.base.DBCursor;
//import com.sequoiadb.base.DBQuery;
//import com.sequoiadb.base.Sequoiadb;
//import com.sequoiadb.base.SequoiadbDatasource;
//import com.sequoiadb.exception.BaseException;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.config.WebConfig;
//import bigdata.config.WebSocketConfig;
//import bigdata.util.EChartUtil;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
////@WebAppConfiguration
//public class Sequoiadb2Test {
//
//	
//	@Resource(name="sequoiadb2Datasource")
//	private SequoiadbDatasource ds;
//	@Resource(name="bigdataMiddleDS")
//	private SequoiadbDatasource ds2;
//	
//	
//	private static final Logger logger=LogManager.getLogger();
//	
//	//@Test
//	public void findCSAndCL() throws BaseException, InterruptedException{
//		Assert.assertNotNull(ds);
//		
//		Sequoiadb sdb=ds.getConnection();
//		
//			DBCursor cur=sdb.exec("list collections");
//			
//			Map<String, List<String>> cs_cl_map=new HashMap<>();
//			while(cur.hasNext()){
//				String cs=cur.getNext().get("Name").toString();
//				System.out.println("col::"+cs);
//			}
//			
//	}
//
//
//	
//	//@Test
//	public void getTest() throws BaseException, InterruptedException {
//		Sequoiadb sdb=ds.getConnection();
//
//		String sql="select Message,HostName,count(_id) as count from lhbbc.dcslog where LogDate.$timestamp >='2016-10-01 00:00:00' and LogDate.$timestamp <='2017-01-01 00:00:00' "
//				+" and Message in ('1 SFC_ERROR','2 SFC_FATAL_ERROR','3 SFC_DATA_FORMAT_ERROR') group by Message , HostName";
//		
//		try {
//			/*
//			DBCursor cursor=sdb.exec("select ErrMessage ,hostname,count(_id) as count from cdbbc.dcslog where log_date >= '2016-10-01 00:00:00' and log_date <= '2016-10-02 01:00:00' "
//					+ " and ErrMessage in ('1 SFC_ERROR','2 SFC_FATAL_ERROR','3 SFC_DATA_FORMAT_ERROR') group by ErrMessage,hostname"
//					);
//			*/
//			DBCursor cursor=sdb.exec(sql);
//			
//			Builder<BSONObject> b=Stream.builder();
//			while(cursor.hasNext()){
//				
//				System.out.println("*** "+cursor.getNext().toString());
//				b.add(cursor.getCurrent());
//			}
//			
//			
//			
//		} finally {
//			// TODO: handle finally clause
//			if(sdb!=null) sdb.disconnect();
//		}
//	}
//	
//	
////	public BSONObject accumulateValue(BSONObject acc,BSONObject val){
////		
////	}
//	
//	//@Test
//	public void aggregateErrGroupByErrMessage() throws BaseException,InterruptedException{
//		Sequoiadb sdb=ds2.getConnection();
//		
//		try {
//			DBCursor cursor=sdb.exec("select * from bigdata.bobcat_errMessage where time >= '2016-01-01 00:00:00' and time <= '2016-11-31 00:00:00' ");
//			
//			BSONObject map=new BasicBSONObject();
//			while(cursor.hasNext()){
//				BSONObject obj=cursor.getNext();
//				System.out.println("time:"+obj.get("time"));
//				BasicBSONList list=(BasicBSONList) obj.get("errs");
//				for(int i=0;i<list.size();i++){
//					BSONObject err=(BSONObject) list.get(i);
//					System.out.println("ERR:"+err.toString());
//
//					if(!map.containsField(err.get("ErrMessage").toString())){
//						map.put(err.get("ErrMessage").toString(), (long)err.get("ErrCount"));
//						
//					}else{
//						map.put(err.get("ErrMessage").toString(), (long)err.get("ErrCount")+(long)map.get(err.get("ErrMessage").toString()));
//					};
//				}
//
//			}
//			
//			System.out.println("MAP:  "+map.toString());
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			if(sdb!=null) sdb.disconnect();
//		}
//	}
//	
//	//@Test
//	public void aggregateErrGroupByHostname() throws BaseException,InterruptedException{
//		Sequoiadb sdb=ds2.getConnection();
//		
//		try {
//			DBCursor cursor=sdb.exec("select * from bigdata.bobcat_hostname where time >= '2016-01-01 00:00:00' and time <= '2016-11-31 00:00:00' ");
//			
//			BSONObject map=new BasicBSONObject();
//			while(cursor.hasNext()){
//				BSONObject obj=cursor.getNext();
//				System.out.println("time:"+obj.get("time"));
//				BasicBSONList list=(BasicBSONList) obj.get("errs");
//				for(int i=0;i<list.size();i++){
//					BSONObject err=(BSONObject) list.get(i);
//					System.out.println("ERR:"+err.toString());
//
//					if(!map.containsField(err.get("hostname").toString())){
//						map.put(err.get("hostname").toString(), (long)err.get("ErrCount"));
//						
//					}else{
//						map.put(err.get("hostname").toString(), (long)err.get("ErrCount")+(long)map.get(err.get("hostname").toString()));
//					};
//				}
//
//			}
//			
//			System.out.println("MAP:  "+map.toString());
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			if(sdb!=null) sdb.disconnect();
//		}
//	}
//	
//
//	
//	//@Test
//	public void getErrCountOfToday() throws BaseException, InterruptedException{
//		
//		String startTime="2016-12-07 00:00:00";
//		String endTime="2017-04-12 00:09:06";
//		
//		Sequoiadb sdb=ds.getConnection();
//
//		Calendar startCal=Calendar.getInstance();
//			startCal.set(2016, 12, 12);
//		Calendar endCal=Calendar.getInstance();
//			endCal.set(2017, 4, 1);
//		
//		BSONTimestamp startTs=new BSONTimestamp((int)(startCal.getTimeInMillis()/1000), 0);
//		BSONTimestamp endTs=new BSONTimestamp((int)(endCal.getTimeInMillis()/1000), 0);
//		
//			
//		
//			DBCursor cur=sdb.exec("select count(_id) as count from lhbbc.dcslog where LogTime >='"+startTs.toString()+"' and LogTime <'"+endTs.toString()+"'");
//			while(cur.hasNext()){
//				System.out.println("count:"+cur.getNext().get("count"));
//			}
//	
//		sdb.disconnect();
//	}
//	
//	
//	//@Test
//	public void testGetErrOfToday() throws BaseException,InterruptedException{
//		
//		String startTime="2016-12-07 00:00:00";
//		String endTime="2016-12-08 00:00:00";
//		
//			// TODO Auto-generated method stub
//			Sequoiadb sdb=null;
//			try {
//				sdb=ds2.getConnection();
//				
//				String sql=new StringBuilder("select time,errs from bigdata.bobcat where time>='")
//						.append(startTime)
//						.append("' and time<'")
//						.append(endTime)
//						.append("'").toString();
//
//					DBCursor cur=sdb.exec(sql);
//					logger.info(sql);
//					Stream<Object> s=Stream.empty();
//					while(cur.hasNext()){
//						BSONObject r=cur.getNext();
//						
//						if(r.get("errs")!=null){
//							System.out.println("@r:"+r);
//							Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
//								mapped.put("hostname", ((BSONObject)k).get("hostname"));
//								mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
//								mapped.put("count", ((BSONObject)k).get("count"));
//								System.out.println("@mapped:"+mapped);
//								return mapped;
//							});
//							
//							s=Stream.concat(s, s1);
//						}
//					}
//					
//					Map<Object, Long> map= s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
//							.collect(groupingBy(r->((BSONObject)r).get("time").toString().substring(0, 10),summingLong(r->(Long)((BSONObject)r).get("count"))));
//		
//					System.out.println("MAP:"+map);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}finally{
//				if(sdb!=null) sdb.disconnect();
//			}
//
//	}
//	
//	
//	//@Test
//	public void getErrOfRange() throws BaseException, InterruptedException{
//		
//		
//		Sequoiadb db=null;
//		
//		db=ds.getConnection();
//		
//		DBQuery query=new DBQuery();
//			
//		query.setMatcher(new BasicBSONObject("LogTime",new BasicBSONObject("$gt",new BasicBSONObject("$timestamp", "2016-10-10 00:00:00"))));
//		query.setMatcher(new BasicBSONObject("LogTime",new BasicBSONObject("$lt",new BasicBSONObject("$timestamp", "2017-02-10 00:00:00"))));
//			
//		query.setSelector(new BasicBSONObject("count(_id)", ""));
//		
//			DBCursor cur=db.getCollectionSpace("lhbbc").getCollection("dcslog").query(query);
//			
//			while(cur.hasNext()){
//				BSONObject res=cur.getNext();
//				System.out.println("count:"+res.toString());
//			}
//	}
//	
//	//@Test
//	public void subMatcherTest() throws BaseException, InterruptedException{
//		
//		Sequoiadb db=ds.getConnection();
//		
//		DBQuery query=new DBQuery();
//			
//		//fail
//		//query.setMatcher(new BasicBSONObject("_id", new BasicBSONObject("$oid", "589d0cae5cdedb94b97613f8").toString()));
//		
//		//ok
//		//query.setMatcher(new BasicBSONObject("HostName", "dcs_server_62:8082"));
//		
//
//		
//		query.setMatcher(new BasicBSONObject("_id",new ObjectId("589d0cae5cdedb94b97613f8")));
//		
//		DBCursor cur=db.getCollectionSpace("lhbbc").getCollection("dcslog").query(query);
//		
//		while(cur.hasNext()){
//			BSONObject res=cur.getNext();
//			System.out.println("count:"+res.toString());
//		}
//		
//	}
//	
//	@Test
//	public void testTimestamp() throws BaseException, InterruptedException{
//		
//		Sequoiadb db=ds.getConnection();
//
//			Calendar startDate=Calendar.getInstance();
//					startDate.set(2017, 1, 1);
//			Calendar endDate=Calendar.getInstance();
//					endDate.set(2017, 3, 12);
//		
//					BSONObject startMatcher=new BasicBSONObject(new BasicBSONObject("LogTime",new BasicBSONObject("$gt", new BSONTimestamp((int)(startDate.getTimeInMillis()/1000), 0))));
//					BSONObject endMatcher=new BasicBSONObject(new BasicBSONObject("LogTime", new BasicBSONObject("$lt", new BSONTimestamp((int)(endDate.getTimeInMillis()/1000), 0))));
//					
//					BasicBSONObject timeMatcher=new BasicBSONObject("$and", new BSONObject[]{startMatcher,endMatcher});
//
//					
//					DBQuery query=new DBQuery();
//						
//						query.setMatcher(timeMatcher);
//						query.setReturnRowsCount(20l);
//						query.setSkipRowsCount(10000l);
//						
//					DBCursor cur=db.getCollectionSpace("lhbbc").getCollection("dcslog").query(query);
//		
//					while(cur.hasNext()){
//						BSONObject obj=cur.getNext();
//						System.out.println("obj::"+obj.toString());
//					}
//					
//	}
//
//}
//
//
//
//
//
