package bigdata.service.sequoia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.base.SequoiadbDatasource;
import com.sequoiadb.exception.BaseException;

import bigdata.util.AggregateTask;
import bigdata.util.DateUtil;
import bigdata.util.EChartUtil;
import bigdata.util.TimeRange;
import bigdata.util.TimeRangeUnit;
import bigdata.util.TimeSlice;
import  static java.util.stream.Collectors.*;

@Service
public class BobcatServiceImpl implements BobcatService{


	private static final Logger logger=LogManager.getLogger();
	
	@Resource(name="sequoiadbDatasource")
	private SequoiadbDatasource sdbDatasource;

	@Resource(name="bigdataMiddleDS")
	private SequoiadbDatasource bigdataMiddleDS;

	private static final String CS="bigdata";
	private static final String CL="bobcat";
	
	@Override
	public Map<Object, Long> countByErrMessage(String startTime, String endTime)
			throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					//.append("bigdata.bobcat")
					.append(CS)
					.append(".")
					.append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<='")
					.append(endTime)
					.append("'").toString();
			
				DBCursor cur=sdb.exec(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					//System.out.println("cur: >time:"+r.get("time")+" errs:"+r.get("errs"));
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
			return s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
					.collect(groupingBy(r->((BSONObject)r).get("ErrMessage"),summingLong(r->(Long)((BSONObject)r).get("count"))));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
		
	}

	@Override
	public Map<Object, Long> countByHostname(String startTime, String endTime)
			throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					//.append("bigdata.bobcat")
					.append(CS)
					.append(".")
					.append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<='")
					.append(endTime)
					.append("'").toString();
			
				DBCursor cur=sdb.exec(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					//System.out.println("cur: >time:"+r.get("time")+" errs:"+r.get("errs"));
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
				
				return s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
						.collect(groupingBy(r->((BSONObject)r).get("hostname"),summingLong(r->(Long)((BSONObject)r).get("count"))));
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
	}

	@Override
	public Map<Object, Long> countByDay(String startTime, String endTime) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					//.append("bigdata.bobcat")
					.append(CS)
					.append(".")
					.append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<'")
					.append(endTime)
					.append("'").toString();

				DBCursor cur=sdb.exec(sql);
				logger.info(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					
					if(r.get("errs")!=null){
						
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
				
				return s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
						.collect(groupingBy(r->((BSONObject)r).get("time").toString().substring(0, 10),summingLong(r->(Long)((BSONObject)r).get("count"))));
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
	}

	@Override
	public Map<Object, Map<Object, Long>> generateTimeChartGroupByErrMessage(String startTime, String endTime,
			TimeRangeUnit timeDust) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					//.append("bigdata.bobcat")
					.append(CS)
					.append(".")
					.append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<='")
					.append(endTime)
					.append("'").toString();
			
				DBCursor cur=sdb.exec(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					//System.out.println("cur: >time:"+r.get("time")+" errs:"+r.get("errs"));
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
				
				if(timeDust==TimeRangeUnit.MONTH){
					return s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
							.collect(groupingBy(r-> ((BSONObject)r).get("ErrMessage"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0, 7) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
				}else if(timeDust==TimeRangeUnit.DAY){
					return s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
							.collect(groupingBy(r-> ((BSONObject)r).get("ErrMessage"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0, 10) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
				}else if(timeDust==TimeRangeUnit.HOUR){
					return s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
							.collect(groupingBy(r-> ((BSONObject)r).get("ErrMessage"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0, 13) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
				}
				
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
	}

	@Override
	public Map<Object, Map<Object, Long>> generateTimeChartGroupByHostname(String startTime, String endTime,
			TimeRangeUnit timeDust) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					//.append("bigdata.bobcat")
					.append(CS)
					.append(".")
					.append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<'")
					.append(endTime)
					.append("'").toString();
			
				DBCursor cur=sdb.exec(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					//System.out.println("cur: >time:"+r.get("time")+" errs:"+r.get("errs"));
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
				

				if(timeDust==TimeRangeUnit.MONTH){
					return s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
							.collect(groupingBy(r-> ((BSONObject)r).get("hostname"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0,7) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
				}else if(timeDust==TimeRangeUnit.DAY){
					return s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
							.collect(groupingBy(r-> ((BSONObject)r).get("hostname"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0,10) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
				}else if(timeDust==TimeRangeUnit.HOUR){
					return s.parallel().filter( r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null )
							.collect(groupingBy(r-> ((BSONObject)r).get("hostname"), groupingBy(k->((BSONObject)k).get("time").toString().substring(0,13) , summingLong( k-> (Long)((BSONObject)k).get("count") ))));
				}

	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
	}

	@Override
	public Map<Object, Long> countByHour(String startTime, String endTime) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					.append(CS)
					.append(".")
					.append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<='")
					.append(endTime)
					.append("'").toString();
			
				DBCursor cur=sdb.exec(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					//System.out.println("cur: >time:"+r.get("time")+" errs:"+r.get("errs"));
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
				
				return s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
						.collect(groupingBy(r->((BSONObject)r).get("time").toString().substring(0, 13),summingLong(r->(Long)((BSONObject)r).get("count"))));
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
	}

	@Override
	public Map<Object, Long> countByMonth(String startTime, String endTime) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=null;
		try {
			sdb=bigdataMiddleDS.getConnection();
			
			String sql=new StringBuilder("select time,errs from ")
					//.append("bigdata.bobcat")
					.append(CS).append(".").append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<='")
					.append(endTime)
					.append("'").toString();
			
				DBCursor cur=sdb.exec(sql);
				Stream<Object> s=Stream.empty();
				while(cur.hasNext()){
					BSONObject r=cur.getNext();
					//System.out.println("cur: >time:"+r.get("time")+" errs:"+r.get("errs"));
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject("time",((BSONObject)r).get("time"));
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}
				}
				
				return s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
						.collect(groupingBy(r->((BSONObject)r).get("time").toString().substring(0, 7),summingLong(r->(Long)((BSONObject)r).get("count"))));
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}
		return null;
	}

	@Override
	public Map<Object,  Long> countLasOneHourGroupByMinute(String startTime, String endTime) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		
		Sequoiadb sdb=sdbDatasource.getConnection();
		try{
			String sql=new StringBuilder("select * from cdbbc.dcslog where log_date >='"+startTime+"' and log_date <'"+endTime+"'").toString();
			
			DBCursor cur=sdb.exec(sql);
			
			Stream<Object> s=Stream.empty();
			
			
			List<BSONObject> buffer=new ArrayList<>();
			int bufferLen=1000;
			int count=0;
			while(cur.hasNext()){
				//BSONObject r=cur.getNext();
					count++;
					buffer.add(cur.getNext());
				if(!cur.hasNext() || count==bufferLen){
					s=Stream.concat(s, buffer.stream());
					buffer=new ArrayList<>();
					
					count=0;
				}
				
			}

			Map<Object, Long> map=s.collect(groupingBy(r ->((BSONObject)r).get("log_date").toString().subSequence(0, 16),counting()));
			System.out.println(">>map<<:"+map);
			return map;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sdb!=null) sdb.disconnect();
		}

		
		return null;
	}

	@Override
	public long countOfToday(String startTime, String endTime) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=sdbDatasource.getConnection();
		
		StringBuilder sb=new StringBuilder("select count(_id) as count from cdbbc.dcslog where log_date>='")
				.append(startTime)
				.append("' and log_date <'")
				.append(endTime)
				.append("' ");
		DBCursor cur=sdb.exec(sb.toString());
		long count=0l;
		while(cur.hasNext()){
			count=(long) cur.getNext().get("count");
			logger.info("count of today-CD:"+count);
		}
		return count;
	}

	@Override
	public Map<String, Long> getErrFromHostnameToErrMessage(String startTime, String endTime)
			throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=bigdataMiddleDS.getConnection();
		
		try {
			String sql=new StringBuilder("select * from ")
					//.append("bigdata.bobcat")
					.append(CS).append(".").append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<'")
					.append(endTime).append("'").toString();
			
			DBCursor cur=sdb.exec(sql);
			Stream<BSONObject> s=Stream.empty();
			while(cur.hasNext()){
				BSONObject r=cur.getNext();
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject();
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}	
					
				}

			Map<String, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
					.collect(Collectors.groupingBy(r-> {

							return ((BSONObject)r).get("hostname").toString()+"@"+((BSONObject)r).get("ErrMessage").toString();
							},summingLong( r-> (Long)((BSONObject)r).get("count") )));
			
			
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(sdb!=null) sdb.disconnect();
		}
		
		return null;
	}

	@Override
	public Map<String, Long> getErrFromErrMessageToHostname(String startTime, String endTime)
			throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=bigdataMiddleDS.getConnection();
		
		try {
			String sql=new StringBuilder("select * from ")
					//.append("bigdata.bobcat")
					.append(CS).append(".").append(CL)
					.append(" where time>='")
					.append(startTime)
					.append("' and time<'")
					.append(endTime).append("'").toString();
			
			DBCursor cur=sdb.exec(sql);
			Stream<BSONObject> s=Stream.empty();
			while(cur.hasNext()){
				BSONObject r=cur.getNext();
					if(r.get("errs")!=null){
						Stream<BSONObject> s1=((BasicBSONList)r.get("errs")).stream().map(k-> {BSONObject mapped=new BasicBSONObject();
							mapped.put("hostname", ((BSONObject)k).get("hostname"));
							mapped.put("ErrMessage", ((BSONObject)k).get("ErrMessage"));
							mapped.put("count", ((BSONObject)k).get("count"));
							return mapped;
						});
						s=Stream.concat(s, s1);
					}	
					
				}

			Map<String, Long> map=s.parallel().filter(r-> ((BSONObject)r).get("hostname")!=null && ((BSONObject)r).get("ErrMessage")!=null)
					.collect(Collectors.groupingBy(r-> {

							return ((BSONObject)r).get("ErrMessage").toString()+"@"+((BSONObject)r).get("hostname").toString();
							},summingLong( r-> (Long)((BSONObject)r).get("count") )));
			
			
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(sdb!=null) sdb.disconnect();
		}
		
		return null;
	}




	
	
}
