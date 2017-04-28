package bigdata.quartz;

import java.io.IOException;
import java.util.Properties;

import org.bson.BSONObject;

import com.sequoiadb.base.Sequoiadb;

public abstract class LogConvertorAdaptor implements LogConvertor {

	
	private String startTime;
	private String endTime;
	
	 static Properties props=null;
	
	static{
		   props=new Properties();
			try {
				props.load(LogConvertorAdaptor.class.getClassLoader().getResourceAsStream("config.properties"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public LogConvertorAdaptor(String startTime, String endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	
	public Sequoiadb getLogSdb(){
		return 	new Sequoiadb(props.getProperty("sequoiadb.url")
					, props.getProperty("sequoiadb.username")
					, props.getProperty("sequoiadb.password"));
	}
	
	public Sequoiadb getMiddleSdb(){
		return 	new Sequoiadb(props.getProperty("bigdata.url")
				, props.getProperty("bigdata.username")
				, props.getProperty("bigdata.password"));
	}
	

	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	

}
