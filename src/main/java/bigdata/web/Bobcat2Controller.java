package bigdata.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.sequoiadb.exception.BaseException;

import bigdata.model.Module;
import bigdata.model.QueryField;
import bigdata.model.QueryFieldDef;
import bigdata.service.sequoia.BobcatService;
import bigdata.service.sequoia.SequoiadbService;
import bigdata.util.D3Util;
import bigdata.util.DateUtil;
import bigdata.util.EChartUtil;
import bigdata.util.Page;
import bigdata.util.PageUtil;
import bigdata.util.ParamCriteria;
import bigdata.util.PageUtil.PageAction;
import bigdata.util.TimeRange;
import bigdata.util.TimeRangeUnit;
import bigdata.util.TimeSlice;

@Controller
@RequestMapping(value="/lhbobcat")
public class Bobcat2Controller {
	
	//@Autowired
	@Resource(name="bobcat2ServiceImpl")
	private BobcatService bobcatService;
	
	private final String cs="lhbbc";
	private final String cl="dcslog";
	
	@Resource(name="sequoiadb2ServiceImpl")
	private SequoiadbService sdbService;


	private Logger logger=LogManager.getLogger();
	

	/**
	 * Chart of hostname!
	 * @param chartType
	 * @param year
	 * @param month
	 * @param millis
	 * @return
	 * @throws BaseException
	 * @throws InterruptedException
	 */
	@RequestMapping(path="/chart/Hostname/{chartType}/year={year}&month={month}&date={millis}",method=RequestMethod.GET)
	public ResponseEntity<BSONObject> findLogGroupByHostname(@PathVariable(name="chartType") String chartType,
																@PathVariable(name="year",required=false) Integer year,
																@PathVariable(name="month",required=false) Integer month,
																@PathVariable(name="millis",required=false) Long millis
														) throws BaseException, InterruptedException{
		
		System.out.println(chartType);
		
		
		TimeRange range=null;

		
		if("day".equals(chartType)){

			Calendar cal=Calendar.getInstance();
				cal.setTimeInMillis(millis);
			range=TimeRange.createBy_Date_Hour(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			
		}else if("month".equals(chartType)){
			
			range=TimeRange.createBy_Month_Day(year, month);

		}else if("year".equals(chartType)){

			range=TimeRange.createBy_Year_Month(year);
		}

		BSONObject converted=EChartUtil.convertTo_TimeChartGroupByHostname(range, bobcatService.generateTimeChartGroupByHostname(range.getStartTimeStr(),range.getEndTimeStr(), range.getTimeFragment()));
		
		return new ResponseEntity<BSONObject>(converted, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(path="/chart/today/count/lastOneHour")
	public ResponseEntity<BSONObject> findErrCountOfLastHourGroupByMinute() throws BaseException,InterruptedException{
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar endCal=Calendar.getInstance();
			endCal.setTime(new Date());
			String endTime=sdf.format(endCal.getTime());
		
		Calendar startCal=Calendar.getInstance();
			startCal.setTime(endCal.getTime());
			startCal.add(Calendar.HOUR_OF_DAY,-1);
			
			String startTime=sdf.format(startCal.getTime());
		
		Map<Object, Long> map=bobcatService.countLasOneHourGroupByMinute(startTime, endTime);
		
		System.out.println("<<map>>:"+map);
		BSONObject converted=EChartUtil.convertTo_LastOneHourSliceByMinute(new Date(), map);

		return new ResponseEntity<BSONObject>(converted, HttpStatus.OK);
		
	}
	
	@RequestMapping(path="/chart/today/count")
	public ResponseEntity<BSONObject> findErrCountOfToday() throws BaseException,InterruptedException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		String startTime=sdf.format(cal.getTime());
			
			cal.add(Calendar.DAY_OF_MONTH, 1);
		String endTime=sdf.format(cal.getTime());
		
		
		logger.debug("startDate:"+startTime+" endDate:"+endTime);

		
		long count=bobcatService.countOfToday(startTime, endTime);
		
		BSONObject result=new BasicBSONObject();
			result.put("count", count);
		
		return new ResponseEntity<BSONObject>(result, HttpStatus.OK);
	}
	
	/**
	 * Chart of error message!
	 * @param chartType
	 * @param year
	 * @param month
	 * @param millis
	 * @return
	 * @throws ParseException
	 * @throws BaseException
	 * @throws InterruptedException
	 */
	
	@RequestMapping(path="/chart/ErrMessage/{chartType}/year={year}&month={month}&date={millis}",method=RequestMethod.GET)
	public ResponseEntity<BSONObject> findLogGroupByErrMessage(@PathVariable(name="chartType") String chartType,
																@PathVariable(name="year",required=false) Integer year,
																@PathVariable(name="month",required=false) Integer month,
																@PathVariable(name="millis",required=false) Long millis
														) throws ParseException, BaseException, InterruptedException{

		System.out.println(chartType);
		
		TimeRange range=null;

		
		if("day".equals(chartType)){

			Calendar cal=Calendar.getInstance();
				cal.setTimeInMillis(millis);
			range=TimeRange.createBy_Date_Hour(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			
		}else if("month".equals(chartType)){
			
			range=TimeRange.createBy_Month_Day(year, month);

		}else if("year".equals(chartType)){

			range=TimeRange.createBy_Year_Month(year);
		}


		BSONObject converted=EChartUtil.convertTo_TimeChartGroupByErrMessage(range, bobcatService.generateTimeChartGroupByErrMessage(range.getStartTimeStr(), range.getEndTimeStr(), range.getTimeFragment()));
		
		return new ResponseEntity<BSONObject>(converted, HttpStatus.OK);
		
		
	}

	/**
	 * Query data !
	 * @param pageIndex
	 * @param pageSize
	 * @param totalPage
	 * @param totalCount
	 * @param pageAction
	 * @param paramValues
	 * @return
	 */
	@RequestMapping(path="/page/pageIndex={pageIndex}&pageSize={pageSize}&action={pageAction}&totalPage={totalPage}&totalCount={totalCount}"
					,method=RequestMethod.POST,consumes={"application/json;charset=utf-8"},produces={"application/json;charset=utf-8"})
	public ResponseEntity<Page> pageQuery(@PathVariable(name="pageIndex",required=false) int pageIndex
										,@PathVariable(name="pageSize",required=false) int pageSize
										,@PathVariable(name="totalPage",required=false) Integer totalPage
										,@PathVariable(name="totalCount",required=false) Long totalCount
										,@PathVariable(name="pageAction",required=false) String pageAction
										,@RequestBody Map<String, Object> paramValues
										){



		String startDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((Long)paramValues.get("start_date")));
		String endDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((Long)paramValues.get("end_date")));
		
		System.out.println("startDate:"+startDate+" endDate:"+endDate);

			PageAction action=PageUtil.valueFrom(pageAction);
			
			Page page=new Page(pageIndex, pageSize, totalPage,totalCount, action);
			
			List<String> fields=null;
			List<ParamCriteria> pcList=new ArrayList<>();
			
			pcList.add(new ParamCriteria("log_date", startDate, ">="));
			pcList.add(new ParamCriteria("log_date", endDate, "<="));
			
			if(paramValues.get("TS")!=null && !"".equals(paramValues.get("TS")))
				pcList.add(new ParamCriteria("TS", paramValues.get("TS"), "="));
			if(paramValues.get("SN")!=null && !"".equals(paramValues.get("SN")))
				pcList.add(new ParamCriteria("SN", paramValues.get("SN"), "="));
			if(paramValues.get("Description")!=null && !"".equals(paramValues.get("Description")))
				pcList.add(new ParamCriteria("Description", paramValues.get("Description"), "like"));
			
			try {
				page=sdbService.findAsPage(cs, cl, fields, pcList, page);
				
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
			
			return new ResponseEntity<Page>(page, HttpStatus.OK);

	}
	
	@RequestMapping(path="/chart/today/hostname")
	public ResponseEntity<BSONObject> getErrOfTodayGroupByHostname() throws BaseException, InterruptedException{
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		String startTime=sdf.format(cal.getTime());
			
			cal.add(Calendar.DAY_OF_MONTH, 1);
		String endTime=sdf.format(cal.getTime());

		logger.debug("startDate:"+startTime+" endDate:"+endTime);
	
		BSONObject converted= EChartUtil.convertTo_Hostname(bobcatService.countByHostname(startTime, endTime));

		return new ResponseEntity<BSONObject>(converted, HttpStatus.OK);
	}
	
	@RequestMapping(path="/chart/today/errMessage")
	public ResponseEntity<BSONObject> getErrOfTodayGroupByErrMessage() throws BaseException, InterruptedException{
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		String startTime=sdf.format(cal.getTime());
			
			cal.add(Calendar.DAY_OF_MONTH, 1);
		String endTime=sdf.format(cal.getTime());
				
		logger.debug("startDate:"+startTime+" endDate:"+endTime);

		BSONObject converted=EChartUtil.convertTo_ErrMessage(bobcatService.countByErrMessage(startTime, endTime));
		return new ResponseEntity<BSONObject>(converted, HttpStatus.OK);
	}

	@RequestMapping(path="/chart/today/hour")
	public ResponseEntity<BSONObject> getErrOfTodayGroupByHour() throws BaseException,InterruptedException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		String startTime=sdf.format(cal.getTime());
			
			cal.add(Calendar.DAY_OF_MONTH, 1);
		String endTime=sdf.format(cal.getTime());
				
		logger.debug("startDate:"+startTime+" endDate:"+endTime);

		BSONObject converted=EChartUtil.converTo_DaySliceByHour(bobcatService.countByHour(startTime, endTime));
		
		return new ResponseEntity<BSONObject>(converted, HttpStatus.OK);
	}
	
	@RequestMapping(path="/chart/sankey/hostnameToErrMessage/{chartType}/year={year}&month={month}&date={millis}")
	public ResponseEntity<BSONObject> getSankeyChartFromHostnameToErrMessage(
			@PathVariable(name="chartType") String chartType,
			@PathVariable(name="year",required=false) Integer year,
			@PathVariable(name="month",required=false) Integer month,
			@PathVariable(name="millis",required=false) Long millis
	) throws ParseException, BaseException, InterruptedException{
		
		System.out.println("chartType:"+chartType+"year:"+year+" month:"+month+" millis:"+millis);
		
		TimeRange range=null;
	
		if("day".equals(chartType)){

			Calendar cal=Calendar.getInstance();
				cal.setTimeInMillis(millis);
			range=TimeRange.createBy_Date_Hour(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			
		}else if("month".equals(chartType)){
			
			range=TimeRange.createBy_Month_Day(year, month);

		}else if("year".equals(chartType)){

			range=TimeRange.createBy_Year_Month(year);
		}

		
		Map<String, Long> map=bobcatService.getErrFromHostnameToErrMessage(range.getStartTimeStr(),range.getEndTimeStr());
		BSONObject chart=D3Util.convertToSankeyChart(map);
		
		if("day".equals(chartType)) chart.put("chartType", range.getStartTimeStr().substring(0,10)+"錯誤桑基圖");
		else if("month".equals(chartType)) chart.put("chartType", range.getStartTimeStr().substring(0,4)+" 年 "+range.getStartTimeStr().substring(5,7)+" 月錯誤桑基圖");
		else if("year".equals(chartType)) chart.put("chartType", range.getStartTimeStr().substring(0, 4)+"年錯誤桑基圖");
		
		return new ResponseEntity<BSONObject>(chart, HttpStatus.OK);
	}
	
	@RequestMapping(path="/chart/sankey/ErrMessageToHostname/{chartType}/year={year}&month={month}&date={millis}")
	public ResponseEntity<BSONObject> getSankeyChartFromErrMessageToHostname(
			@PathVariable(name="chartType") String chartType,
			@PathVariable(name="year",required=false) Integer year,
			@PathVariable(name="month",required=false) Integer month,
			@PathVariable(name="millis",required=false) Long millis
	) throws ParseException, BaseException, InterruptedException{
		
		System.out.println("chartType:"+chartType+"year:"+year+" month:"+month+" millis:"+millis);
		
		TimeRange range=null;
	
		if("day".equals(chartType)){

			Calendar cal=Calendar.getInstance();
				cal.setTimeInMillis(millis);
			range=TimeRange.createBy_Date_Hour(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			
		}else if("month".equals(chartType)){
			
			range=TimeRange.createBy_Month_Day(year, month);

		}else if("year".equals(chartType)){

			range=TimeRange.createBy_Year_Month(year);
		}

		
		Map<String, Long> map=bobcatService.getErrFromErrMessageToHostname(range.getStartTimeStr(), range.getEndTimeStr());
		BSONObject chart=D3Util.convertToSankeyChart(map);
		
		return new ResponseEntity<BSONObject>(chart, HttpStatus.OK);
	}

}
