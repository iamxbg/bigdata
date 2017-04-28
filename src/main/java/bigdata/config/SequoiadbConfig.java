package bigdata.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.base.SequoiadbDatasource;
import com.sequoiadb.base.SequoiadbOption;
import com.sequoiadb.exception.BaseException;
import com.sequoiadb.net.ConfigOptions;

@Configuration
public class SequoiadbConfig {

	@Bean(name="sdb")
	public Sequoiadb sdb() throws BaseException, InterruptedException, FileNotFoundException, IOException{
		SequoiadbDatasource sdbDatasource=sequoiadbDatasource();
			return sdbDatasource.getConnection();
	}
	
	
	@Bean(name="sequoiadbDatasource")
    public SequoiadbDatasource sequoiadbDatasource() throws FileNotFoundException, IOException {

			
		Properties props=new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

    	
        SequoiadbDatasource ds = null;
        Sequoiadb db = null;
        ArrayList<String> urls = new ArrayList<String>();
        ConfigOptions nwOpt = new ConfigOptions();        // 定义连接选项
        SequoiadbOption dsOpt = new SequoiadbOption();    // 定义连接池选项
        //監控端口: 8001
        urls.add(props.getProperty("sequoiadb.url"));
      
        nwOpt.setConnectTimeout(500);                     // 设置若连接失败，超时时间（ms）
        nwOpt.setMaxAutoConnectRetryTime(0);              // 设置若连接失败，重试次数
        // 以下设置的都是 SequoiadbOption 的默认值
        dsOpt.setMaxConnectionNum(1000);                   // 设置连接池最大连接数
        dsOpt.setInitConnectionNum(10);                   // 初始化连接池时，创建连接的数量
        dsOpt.setDeltaIncCount(10) ;                      // 当池中没有可用连接时，增加连接的数量
        dsOpt.setMaxIdeNum(10);                           // 周期清理多余的空闲连接时，应保留连接的数量
        dsOpt.setTimeout(5 * 1000);                 // 当已使用的连接数到达设置的最大连接数时（500），请求连接的等待时间。
        dsOpt.setAbandonTime(10 * 60 * 1000);             // 连接存活时间，当连接空闲时间超过连接存活时间，将被连接池丢弃
        dsOpt.setRecheckCyclePeriod(1 * 60 * 1000);       // 清除多余空闲连接的周期
        dsOpt.setRecaptureConnPeriod(10 * 60 * 1000);     // 检测并取回异常地址的周期
        
        ds = new SequoiadbDatasource(urls, props.getProperty("sequoiadb.username"), props.getProperty("sequoiadb.password"), nwOpt, dsOpt); // 创建连接池

        return ds;
    }
	
	
	@Bean(name="sequoiadb2Datasource")
    public SequoiadbDatasource sequoiadbDatasource2() throws FileNotFoundException, IOException {

			
			Properties props=new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

    	
        SequoiadbDatasource ds = null;
        Sequoiadb db = null;
        ArrayList<String> urls = new ArrayList<String>();
        ConfigOptions nwOpt = new ConfigOptions();        // 定义连接选项
        SequoiadbOption dsOpt = new SequoiadbOption();    // 定义连接池选项
        //監控端口: 8001
        urls.add(props.getProperty("sequoiadb2.url"));
      
        nwOpt.setConnectTimeout(500);                     // 设置若连接失败，超时时间（ms）
        nwOpt.setMaxAutoConnectRetryTime(0);              // 设置若连接失败，重试次数
        // 以下设置的都是 SequoiadbOption 的默认值
        dsOpt.setMaxConnectionNum(1000);                   // 设置连接池最大连接数
        dsOpt.setInitConnectionNum(10);                   // 初始化连接池时，创建连接的数量
        dsOpt.setDeltaIncCount(10) ;                      // 当池中没有可用连接时，增加连接的数量
        dsOpt.setMaxIdeNum(10);                           // 周期清理多余的空闲连接时，应保留连接的数量
        dsOpt.setTimeout(5 * 1000);                 // 当已使用的连接数到达设置的最大连接数时（500），请求连接的等待时间。
        dsOpt.setAbandonTime(10 * 60 * 1000);             // 连接存活时间，当连接空闲时间超过连接存活时间，将被连接池丢弃
        dsOpt.setRecheckCyclePeriod(1 * 60 * 1000);       // 清除多余空闲连接的周期
        dsOpt.setRecaptureConnPeriod(10 * 60 * 1000);     // 检测并取回异常地址的周期
        
        ds = new SequoiadbDatasource(urls, props.getProperty("sequoiadb2.username"), props.getProperty("sequoiadb2.password"), nwOpt, dsOpt); // 创建连接池

        return ds;
    }
	
	
	@Bean(name="bigdataMiddleDS")
    public SequoiadbDatasource bigdataMiddleDS() throws IOException {
    	
		Properties props=new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
		
        SequoiadbDatasource ds = null;
        Sequoiadb db = null;
        ArrayList<String> urls = new ArrayList<String>();
        ConfigOptions nwOpt = new ConfigOptions();        // 定义连接选项
        SequoiadbOption dsOpt = new SequoiadbOption();    // 定义连接池选项
        //監控端口: 8001
        urls.add(props.getProperty("bigdata.url"));
      
       
        nwOpt.setConnectTimeout(1000);                     // 设置若连接失败，超时时间（ms）
        nwOpt.setMaxAutoConnectRetryTime(0);              // 设置若连接失败，重试次数
        // 以下设置的都是 SequoiadbOption 的默认值
        dsOpt.setMaxConnectionNum(1000);                   // 设置连接池最大连接数
        dsOpt.setInitConnectionNum(10);                   // 初始化连接池时，创建连接的数量
        dsOpt.setDeltaIncCount(10) ;                      // 当池中没有可用连接时，增加连接的数量
        dsOpt.setMaxIdeNum(10);                           // 周期清理多余的空闲连接时，应保留连接的数量
        dsOpt.setTimeout(5 * 1000);                 // 当已使用的连接数到达设置的最大连接数时（500），请求连接的等待时间。
        dsOpt.setAbandonTime(10 * 60 * 1000);             // 连接存活时间，当连接空闲时间超过连接存活时间，将被连接池丢弃
        dsOpt.setRecheckCyclePeriod(1 * 60 * 1000);       // 清除多余空闲连接的周期
        dsOpt.setRecaptureConnPeriod(10 * 60 * 1000);     // 检测并取回异常地址的周期
        
        
        ds = new SequoiadbDatasource(urls, props.getProperty("bigdata.username"), props.getProperty("bigdata.password"), nwOpt, dsOpt); // 创建连接池

        return ds;
    }
	
	
	
	
	
	
	
}
