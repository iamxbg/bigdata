//package bigdata;
//
//import javax.annotation.Resource;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sequoiadb.base.SequoiadbDatasource;
//import com.sequoiadb.exception.BaseException;
//
//import bigdata.config.RootConfig;
//import bigdata.config.SequoiadbConfig;
//import bigdata.config.WebConfig;
//
//@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ConfigTest {
//
//	@Resource(name="sequoiadb2Datasource")
//	private SequoiadbDatasource sequoiadbDatasource;
//	
//	
//	public ConfigTest() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	@Test
//	public void testConnection() throws BaseException, InterruptedException{
//		Assert.assertNotNull(sequoiadbDatasource);
//		Assert.assertNotNull(sequoiadbDatasource.getConnection());
//	}
//
//}
