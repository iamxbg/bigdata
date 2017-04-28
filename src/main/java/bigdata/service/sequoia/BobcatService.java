package bigdata.service.sequoia;

import java.util.Date;
import java.util.Map;

import org.bson.BSONObject;

import com.sequoiadb.exception.BaseException;

import bigdata.util.TimeRange;
import bigdata.util.TimeRangeUnit;

public interface BobcatService {
	
	public Map<Object, Long> countByErrMessage(String startTime,String endTime) throws BaseException,InterruptedException;
	
	public Map<Object, Long> countByHostname(String startTime,String endTime) throws BaseException,InterruptedException;
	
	public Map<Object, Long> countByHour(String startTime,String endTime) throws BaseException,InterruptedException;
	
	public Map<Object, Long> countByDay(String startTime,String endTime) throws BaseException,InterruptedException;
	
	public Map<Object, Long> countByMonth(String startTime,String endTime) throws BaseException,InterruptedException;
	
	public Map<Object, Map<Object,Long>> generateTimeChartGroupByErrMessage(String startTime,String endTime,TimeRangeUnit timeDust) throws BaseException,InterruptedException;
	
	public Map<Object, Map<Object,Long>> generateTimeChartGroupByHostname(String startTime,String endTime,TimeRangeUnit timeDust) throws BaseException,InterruptedException;
	
	public Map<Object, Long> countLasOneHourGroupByMinute(String startTime,String endTime)  throws BaseException,InterruptedException;
	
	public long countOfToday(String startTime,String endTime) throws BaseException,InterruptedException;
	
	
	public Map<String, Long> getErrFromHostnameToErrMessage(String startTime,String endTime) throws BaseException,InterruptedException;
	
	public Map<String, Long> getErrFromErrMessageToHostname(String startTime,String endTime) throws BaseException,InterruptedException;
	
}
