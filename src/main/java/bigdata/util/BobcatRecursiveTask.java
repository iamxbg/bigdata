package bigdata.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;

@Deprecated
public class BobcatRecursiveTask extends RecursiveTask<BSONObject> {
	
	private Sequoiadb sdb;
	
	private TimeSlice slice;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	public BobcatRecursiveTask(Sequoiadb sdb, TimeSlice slice) {
		super();
		this.sdb = sdb;
		this.slice = slice;
	}


	@Override
	protected BSONObject compute() {
		// TODO Auto-generated method stub

		try {
			Date start=slice.getStart();
			Date end=slice.getEnd();

			String starttime=sdf.format(start);
			String endtime=sdf.format(end);

			String sql="select ErrMessage,count(ErrMessage) as ErrMessage_count from cdbbc.dcslog where log_date>='"+starttime+"' and log_date<='"+endtime+"'"
			+" and ErrMessage in ('1 SFC_ERROR','2 SFC_FATAL_ERROR','3 SFC_DATA_FORMAT_ERROR','5 SFC_UNKNOW_STATION_ID','13 SFC_UKNOWN_RESPONSE') "
			+" group by ErrMessage";
			
			BasicBSONList list=null;
			
			DBCursor cursor=sdb.exec(sql);
			while(cursor.hasNext()){
				list=(BasicBSONList) cursor.getNext();
				
			}
			
			BSONObject filterResult=new BasicBSONObject();
						
			for(int i=0;i<list.size();i++){
				
				BSONObject obj=(BSONObject) list.get(i);
				
				if(obj.get("ErrMessage")!=null){
					filterResult.put((String)obj.get("ErrMessage"), obj.get("ErrMessage_count"));
				}
				
			}
			
			BSONObject dataUnit=new BasicBSONObject();
				dataUnit.put("date",sdf.format(start));
				dataUnit.put("dataMap", filterResult);

				
			return dataUnit;
		} finally {
			// TODO: handle finally clause
			sdb.disconnect();
		}
			

	}

}
