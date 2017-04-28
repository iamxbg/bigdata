//package bigdata.mock;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.config.WebConfig;
//import bigdata.config.WebSocketConfig;
//import bigdata.model.User;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//
//import org.bson.BSONObject;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class,WebConfig.class,WebSocketConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//public class UserControllerTest {
//	
//	@Autowired
//	private WebApplicationContext wac;
//
//	private MockMvc mvc;
//	
//	@Before 
//	public void setup() {
//		this.mvc =
//		MockMvcBuilders.webAppContextSetup
//		(this.wac).build();
//		}
//	
//	@Test
//	public void testAddUser(){
//		
//		String name="Jakcson";
//		String username="Bili";
//		String password="werwer";
//		String work_id="erwr";
//		
//		User user=new User(name, username, password, work_id);
//		BSONObject userBSON=User.parseToBSON(user);
//		
//		System.out.println("add User BSON::"+userBSON.toString());
//		
//		
//		try {
//			this.mvc.perform(post("/user/addUser").accept(MediaType.APPLICATION_JSON_UTF8).content(userBSON.toString().getBytes("UTF-8")))
//					.andExpect(status().is(200));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//	}
//}
