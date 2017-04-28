package bigdata.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public PropertiesUtil() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static Properties props=new Properties();
	
	static{
		InputStream inStream=PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			props.load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String  getSequoiadbUrl(FactoryArea area){
		
		if(area==FactoryArea.CD) return props.getProperty("sequoiadb.url");
		else if(area==FactoryArea.LH) return props.getProperty("sequoiadb2.url");
		else if(area==FactoryArea.BIGDATA_MIDDLE) return props.getProperty("bigdata.url");
		return null;
	}
	
	public static String getSequoiadbUsername(FactoryArea area){
		if(area==FactoryArea.CD) return props.getProperty("sequoiadb.username");
		else if(area==FactoryArea.LH) return props.getProperty("sequoiadb2.username");
		else if(area==FactoryArea.BIGDATA_MIDDLE) return props.getProperty("bigdata.userame");
		return null;

	}
	
	public static String getSequoiadnPassword(FactoryArea area){
		if(area==FactoryArea.CD) return props.getProperty("sequoiadb.password");
		else if(area==FactoryArea.LH) return props.getProperty("sequoiadb2.password");
		else if(area==FactoryArea.BIGDATA_MIDDLE) return props.getProperty("bigdata.password");
		return null;
	}
	
	
	public static String getCollectionSpaceName_RAW(FactoryArea area){
		if(area==FactoryArea.CD) return props.getProperty("sequoiadb.cs");
		else if(area==FactoryArea.LH) return props.getProperty("sequoiadb2.cs");
		return null;
	}
	
	public static String getCollectionName_RAW(FactoryArea area){
		if(area==FactoryArea.CD) return props.getProperty("sequoiadb.cl");
		else if(area==FactoryArea.LH) return props.getProperty("sequoiadb2.cl");
		return null;
	}
	
	public static String getCollectionSpaceName_MIDDLE(FactoryArea area){
		if(area==FactoryArea.CD) return props.getProperty("bigdata.bobcat.cs");
		else if(area==FactoryArea.LH) return props.getProperty("bigdata.lhbobcat.cs");
		return null;
	}
	
	public static String getCollectionName_MIDDEL(FactoryArea area){
		if(area==FactoryArea.CD) return props.getProperty("bigdata.bobcat.cl");
		else if(area==FactoryArea.LH) return props.getProperty("bigdata.lhbobcat.cl");
		return null;
	}
	

}
