package bigdata.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;


public class Bobcat2_LogStatisticJob implements Job {

	private static Logger logger=LogManager.getLogger();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		logger.log(Level.INFO, "#type: statistic by hostname");
		
		Date now=new Date();

		Calendar thisHour=Calendar.getInstance();
			thisHour.setTime(now);
			thisHour.set(Calendar.MINUTE, 0);
			thisHour.set(Calendar.SECOND, 0);
			
		Calendar prevHour=Calendar.getInstance();
			prevHour.setTime(now);
			prevHour.set(Calendar.MINUTE, 0);
			prevHour.set(Calendar.SECOND, 0);
			prevHour.add(Calendar.HOUR_OF_DAY, -1);
			
		String startTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prevHour.getTime());
		String endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(thisHour.getTime());

		logger.log(Level.INFO,"startTime: "+startTime+" endTime:"+endTime);
		
		new Bobcat2_LogConvertor(startTime, endTime).doStatistics();

	}

}
