package bigdata.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import static bigdata.util.PropertiesUtil.*;

import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;

import bigdata.util.FactoryArea;

public  class BobcatJobAuditJob implements Job{

	private static Logger logger=LogManager.getLogger();
	
	private Sequoiadb sdb=null;
	private FactoryArea area;
	
	 public BobcatJobAuditJob(FactoryArea area) {
		// TODO Auto-generated constructor stub
		this.area=area;
		this.sdb=new Sequoiadb(getSequoiadbUrl(FactoryArea.BIGDATA_MIDDLE)
				,getSequoiadbUsername(FactoryArea.BIGDATA_MIDDLE)
				,getSequoiadnPassword(FactoryArea.BIGDATA_MIDDLE));
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		 
	
		 //check the lastest data time
		 
		 DBCollection col=sdb.getCollectionSpace(getCollectionName_RAW(area))
		 	.getCollection(getCollectionName_RAW(area));
		 
		 	long count=col.getCount();
		 	DBQuery matcher=new DBQuery();
		 			matcher.setSkipRowsCount(count-1);
		 				 
		 	DBCursor cur=col.query(matcher);
		 	
		 	while(cur.hasNext()){
		 		BSONObject obj=cur.getNext();
		 		String dateStr=(String) obj.get("log_date");
		 		logger.info("date:"+dateStr);
		 	}
		 	
		 	
		 	
	
	}
	
	 




}
