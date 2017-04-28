import static bigdata.util.PropertiesUtil.getCollectionName_RAW;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;

import bigdata.util.FactoryArea;
import static bigdata.util.PropertiesUtil.*;

@RunWith(JUnit4.class)
public class FooTest {

	public FooTest() {
		// TODO Auto-generated constructor stub
	}
	
	//@Test
	public void convertTimeStringToMillis() throws ParseException{
		String startTimeStr="2017-04-10 00:00:00";
		String endTimeStr="2017-04-20 00:00:00";
		
		System.out.println("startMillis:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStr).getTime()+" endMillis:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr).getTime());
	}
	
	@Test
	public void testGetLastTime(){
		Sequoiadb sdb=new Sequoiadb("10.244.134.173:11810","sdbadmin", "sdbadmin");
		DBCollection col=sdb.getCollectionSpace(getCollectionSpaceName_RAW(FactoryArea.CD))
			 	.getCollection(getCollectionName_RAW(FactoryArea.CD));
			 
			 	long count=col.getCount();
			 	System.out.println("count:"+count);
			 	DBQuery matcher=new DBQuery();
			 			matcher.setOrderBy(new BasicBSONObject("log_date", "-1"));
			 			matcher.setReturnRowsCount(1l);
			 			matcher.setSelector(new BasicBSONObject());

			 	DBCursor cur=col.query(matcher);

			 	
			 	while(cur.hasNext()){
			 		System.out.println("-----");
			 		BSONObject obj=cur.getNext();
			 		String dateStr=(String) obj.get("log_date");
			 		System.out.println("date:"+dateStr);
			 	}
		
	}

}
