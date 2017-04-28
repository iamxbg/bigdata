package bigdata;
//package bigdata;
//
//import javax.annotation.Resource;
//
//import org.bson.BSONObject;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sequoiadb.base.DBCursor;
//import com.sequoiadb.base.Sequoiadb;
//import com.sequoiadb.base.SequoiadbDatasource;
//import com.sequoiadb.exception.BaseException;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.model.User;
//import bigdata.service.sequoia.sys.UserService;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class UserTest {

//	@Autowired
//	private UserService userService;
//	@Resource(name="sequoiadbDatasource")
//	private SequoiadbDatasource ds;
//	
//
//	//@Test
//	public void addModuleToUser() throws BaseException, InterruptedException{
//		
//		Assert.assertNotNull(ds);
//		
//		String userId="58080bdb770d8397f155d25d";
//		String moduleId="5801d885ee1a101c26000000";
//		
//		
//	}
//	
//
//	//@Test
//	public void testRemoveModuleFromUser() throws BaseException, InterruptedException{
//		String userId="58080bdb770d8397f155d25d";
//		String moduleId="580580c91467aa04d3426b62";
//		
//		userService.removeModuleFromUser(userId, moduleId);
//	}
//	
//	@Test
//	public void testAddUser_base() throws BaseException, InterruptedException{
//		String username="wilson";
//		String password="dude";
//		String name="王天友";
//		String work_id="C3406498";
//		
//		User user=new User(name, username, password, work_id);
//		
//		userService.add(user);
//	}
//}
