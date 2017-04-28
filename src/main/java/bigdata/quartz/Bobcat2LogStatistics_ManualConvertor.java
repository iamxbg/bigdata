package bigdata.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bigdata.config.RootConfig;
import bigdata.config.SequoiadbConfig;
import bigdata.util.DateUtil;
import bigdata.util.TimeRangeUnit;
import bigdata.util.TimeSlice;

@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class Bobcat2LogStatistics_ManualConvertor {
	
	private static Logger logger=LogManager.getLogger(Bobcat2LogStatistics_ManualConvertor.class);

	@Test
	public void get_LogStatistics() throws InterruptedException, ParseException{

		Date start=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-27 00:00:00");
		Date end=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-28 00:00:00");
		
		List<TimeSlice> tsList=DateUtil.ConvertToTimeSliceList(start, end, TimeRangeUnit.HOUR);
		for(int i=0;i<tsList.size();i++){

			final String startTime=tsList.get(i).getStartTimeStr();
			final String endTime=tsList.get(i).getEndTimeStr();
			
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					LogConvertor convertor=new Bobcat2_LogConvertor(startTime, endTime);
					convertor.doStatistics();						
				}
			});

			t.start();
			t.join();

			Thread.sleep(1000);
		}

	}
	
	
}
