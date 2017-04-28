//package bigdata;
//
//import static java.util.stream.Collectors.groupingBy;
//import static java.util.stream.Collectors.summingLong;
//
//import java.util.ArrayList;
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
//import org.bson.types.BasicBSONList;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.sequoiadb.base.DBCursor;
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
//public class SequoiadbTest {
//
//	
//	@Resource(name="sequoiadbDatasource")
//	private SequoiadbDatasource ds;
//	@Resource(name="bigdataMiddleDS")
//	private SequoiadbDatasource ds2;
//	
//	
//	private static final Logger logger=LogManager.getLogger();
//	
//	@Test
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
//		try {
//			DBCursor cursor=sdb.exec("select ErrMessage ,hostname,count(_id) as count from cdbbc.dcslog where log_date >= '2016-10-01 00:00:00' and log_date <= '2016-10-02 01:00:00' "
//					+ " and ErrMessage in ('1 SFC_ERROR','2 SFC_FATAL_ERROR','3 SFC_DATA_FORMAT_ERROR') group by ErrMessage,hostname"
//					);
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
//	@Test
//	public void getErrCountOfToday() throws BaseException, InterruptedException{
//		
//		String startTime="2016-12-07 00:00:00";
//		String endTime="2016-12-08 00:09:06";
//		
//		Sequoiadb sdb=ds.getConnection();
//		
//			DBCursor cur=sdb.exec("select count(_id) as count from cdbbc.dcslog where log_date>='"+startTime+"' and log_date<'"+endTime+"'");
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
//}
