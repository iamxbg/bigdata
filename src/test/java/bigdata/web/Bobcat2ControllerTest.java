//package bigdata.web;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.config.WebConfig;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class,WebConfig.class,SequoiadbConfig.class})
//public class Bobcat2ControllerTest {
//
//	private MockMvc mvc;
//	@Autowired
//	private WebApplicationContext wac;
//	
//	
//	@Before
//	public void before(){
//		this.mvc=MockMvcBuilders.webAppContextSetup(wac).build();
//	}
//	
//	public Bobcat2ControllerTest() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	
//	public static void main(String[] args){
//		Calendar start_cal=Calendar.getInstance();
//			start_cal.set(2017, 1, 14);
//		Calendar end_cal=Calendar.getInstance();
//			end_cal.set(2017, 2, 15);
//			
//		
//		System.out.println("startTime:"+start_cal.getTimeInMillis()+" endTime:"+end_cal.getTimeInMillis());
//	}
//	
//	//@Test
//	public void testPageQuery() throws Exception{
//		mvc.perform(
//				post("/lhbobcat/page/pageIndex=1&pageSize=10&action=next&totalPage=0&totalCount=0")
//					.param("start_date", Long.toString(new Date("2017-02-14").getTime()))
//					.param("end_date",Long.toString(new Date("2017-02-20").getTime()))
//					.accept("application/json;charset=utf-8")
//				).andDo(print());
//	}
//	
//	
//	
//
//}
