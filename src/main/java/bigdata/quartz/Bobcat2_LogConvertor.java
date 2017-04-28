package bigdata.quartz;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;

public class Bobcat2_LogConvertor extends LogConvertorAdaptor2 {
	
	//private static final String[] ERR_TYPES=new String[]{"1 SFC_ERROR","2 SFC_FATAL_ERROR","3 SFC_DATA_FORMAT_ERROR","5 SFC_UKNOWN_STATION_ID","13 SFC_UKNOWN_RESPONSE"};

	public Bobcat2_LogConvertor(String startTime, String endTime) {
		super(startTime, endTime);
		// TODO Auto-generated constructor stub
	}


	private static final Logger logger=LogManager.getLogger();
	
	
	
	public BSONObject statistics(){
		Sequoiadb sdb=null;
		BSONObject result=new BasicBSONObject("time",getStartTime());
		
		sdb=getLogSdb();
		
		StringBuilder sb=new StringBuilder("select ErrMessage,hostname,count(_id) as count from lhbbc.dcslog where log_date >= '")
					.append(getStartTime())
					.append("' and log_date <'")
					.append(getEndTime())
					//.append("' and ErrMessage <> null and hostname <> null and ErrMessage in (");
					.append("' and ErrMessage <> null and hostname <> null ");
			/*		
			for(int i=0;i<ERR_TYPES.length;i++){
				if(i>0)sb.append(",");
				sb.append("'").append(ERR_TYPES[i]).append("'");
			}
				sb.append(")  " );*/
					sb.append(" group by ErrMessage,hostname ");
					String SQL=sb.toString();

		try{

				 DBCursor cur=sdb.exec(SQL);
				 BasicBSONList list=new BasicBSONList();
				 while(cur.hasNext()){
					 list.add(cur.getNext());
				 }
				 
				 result.put("errs", list);
				 
				return result;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if(sdb!=null) sdb.disconnect();
			}
			
			return null;
		
				
		
	}

	
	
	public void doStatistics(){
		Sequoiadb sdb2=null;
		try {
			
			sdb2=getMiddleSdb();
			
			System.out.println(props.get("bigdata.lhbobcat.cs")+" # "+props.getProperty("bigdata.lhbobcat.cl"));
			
			DBCollection col=sdb2.getCollectionSpace(super.props.getProperty("bigdata.lhbobcat.cs"))
								 .getCollection(super.props.getProperty("bigdata.lhbobcat.cl"));
			
			
			
			BSONObject index=new BasicBSONObject("time", new BasicBSONObject("$et", getStartTime()));
			 
			DBQuery query=new DBQuery();
					query.setMatcher(index);
					
			DBCursor cursor=col.query(query);
			
				BSONObject result=statistics();
				
					if(!cursor.hasNext()){
						col.insert(result);
						
						logger.log(Level.DEBUG,"Inserted @" +getStartTime());
						logger.log(Level.DEBUG,"result:@"+result);
						
					}else{
						DBQuery modi=new DBQuery();
							modi.setMatcher(new BasicBSONObject("time", new BasicBSONObject("$et", getStartTime())));
							
							modi.setModifier(new BasicBSONObject("$set",result));
						col.update(modi);
						
						logger.log(Level.DEBUG,"! replace : +"+getStartTime());
						logger.log(Level.DEBUG,"result:@"+result);
					}
		

		} finally {
			// TODO: handle finally clause
			if(sdb2!=null) sdb2.disconnect();
		}		
	}
	
	
}
