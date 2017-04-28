package bigdata.dao.sequoia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.base.SequoiadbDatasource;
import com.sequoiadb.exception.BaseException;
import bigdata.util.Page;
import bigdata.util.ParamCriteria;
import bigdata.util.SequoiaUtil.QueryType;
import bigdata.util.TimeSlice;

@Repository
public class Sequoiadb2DaoImpl implements SequoiadbDao{

	@Resource(name="sequoiadb2Datasource")
	private SequoiadbDatasource sdbDS;
	
	public static Logger logger=LogManager.getLogger();

	@Override
	public BasicBSONList findAsPage(List<String> fields, String sqlClause) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		
		Sequoiadb sdb=sdbDS.getConnection();
		
		try{
			// generate projection clause
			StringBuilder sb=new StringBuilder("select ");

				if(fields!=null){
					for(int i=0;i<fields.size();i++){
						sb.append(fields.get(i));
						if(i!=fields.size()-1) sb.append(" , ");
					}
				}else sb.append(" * ");

			// generate from clause
			
			sb.append(sqlClause);
			
			DBCursor cursor=sdb.exec(sb.toString());
			
	
				BasicBSONList result=new BasicBSONList();
				
				while(cursor.hasNext()){
					BSONObject next=cursor.getNext();
					result.add(next);
				}
				return result;
				

		}finally{
			if(sdb!=null) sdb.disconnect();
		}

		
	}


	@Override
	public long findCount(String sqlClause) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		Sequoiadb sdb=sdbDS.getConnection();
		
		try{
			// generate projection clause
			StringBuilder sb=new StringBuilder("select ");
 
			 sb.append(" count(_id)  as _id_count ");

			// generate from clause

			sb.append(sqlClause);
			
			
			logger.info("* count-SQL:"+sb.toString());

			DBCursor cursor=sdb.exec(sb.toString());
			long count=0;
			try {
				 count=Long.valueOf(cursor.getNext().get("_id_count").toString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return count;
				

		}finally{
			if(sdb!=null) sdb.disconnect();
		}
	}
	
}
