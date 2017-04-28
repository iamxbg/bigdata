package bigdata.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;


@WebListener
public class BobcatStatisticScheduler extends QuartzInitializerListener{

	private Scheduler sche=null;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		try {
			sche = StdSchedulerFactory.getDefaultScheduler();
		
			// job_1 , trigger_1
			JobDetail job_1=newJob(Bobcat_LogStatisticJob.class)
					.withIdentity("job_bobcat", "bobcatLogStatis")
					.build();
		
			Trigger trigger_1=newTrigger()
						.withIdentity("trigger_bobcat", "bobcatLogStatis")
						.startNow()
						//.withSchedule(cronSchedule("0/5 * * * * ?"))
						.withSchedule(cronSchedule("0 40 * * * ?"))
						.build();
			
			/*
			JobDetail job_2=newJob(Bobcat2_LogStatisticJob.class)
					.withIdentity("job_lhbobcat", "lhbobcatLogStatis")
					.build();
			
			Trigger trigger_2=newTrigger()
							.withIdentity("trigger_lhbobcat","lhbobcatLogStatis")
							.startNow()
							.withSchedule(cronSchedule("0 30 * * * ?"))
							.build();
			*/	
		

			
		sche.scheduleJob(job_1, trigger_1);
		//sche.scheduleJob(job_2,trigger_2);
		sche.start();
		
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//super.contextDestroyed(sce);
		if(sche!=null)
			try {
				sche.shutdown(false);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
