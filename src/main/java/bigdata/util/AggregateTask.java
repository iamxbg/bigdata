package bigdata.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;
@Deprecated
public class AggregateTask implements Callable<BSONObject>{
	
	private Sequoiadb sdb;
	
	private TimeSlice slice;
	
	

	public AggregateTask(Sequoiadb sdb, TimeSlice slice) {
		super();
		this.sdb = sdb;
		this.slice = slice;
	}



	@Override
	public BSONObject call() throws Exception {
		// TODO Auto-generated method stub


			Date start=slice.getStart();
			Date end=slice.getEnd();
			
			System.out.println(">>>  startTime:"+slice.getStartTimeStr()+"  :::   endTime: "+slice.getEndTimeStr()+" <<<");
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String starttime=sdf.format(start);
			String endtime=sdf.format(end);
			
			
			String sql="select ErrMessage,count(ErrMessage) as ErrMessage_count from cdbbc.dcslog where log_date>='"+starttime+"' and log_date<='"+endtime+"'"
			+" and ErrMessage in ('1 SFC_ERROR','2 SFC_FATAL_ERROR','3 SFC_DATA_FORMAT_ERROR','5 SFC_UNKNOW_STATION_ID','13 SFC_UKNOWN_RESPONSE') "
			+" group by ErrMessage";
			
			
			BasicBSONList list=null;
			
			DBCursor cursor=sdb.exec(sql);
			while(cursor.hasNext()){
				BSONObject obj=cursor.getNext();
			}
			
			BSONObject filterResult=new BasicBSONObject();
				
			
			for(int i=0;i<list.size();i++){
				
				BSONObject obj=(BSONObject) list.get(i);
				
				if(obj.get("ErrMessage")!=null){
					System.out.println("\t\t%%% ErrMessage:"+(String)obj.get("ErrMessage")+"  ErrMessage_count:"+obj.get("ErrMessage_count"));
					filterResult.put((String)obj.get("ErrMessage"), obj.get("ErrMessage_count"));
				}
				
			}
			
			BSONObject dataUnit=new BasicBSONObject();
				dataUnit.put("date",sdf.format(start));
				System.out.println(" #date:"+sdf.format(start));
				dataUnit.put("dataMap", filterResult);
				System.out.println(" #dataMap:"+filterResult.toString());

				
			return dataUnit;
		
	}

}
