package bigdata.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.base.SequoiadbDatasource;
import com.sequoiadb.exception.BaseException;

public class SequoiaUtil {

	@Resource(name="sequoiadbDatasource")
	static
	 SequoiadbDatasource sdbDatasource;
	
	public enum QueryType{
		data,count
	}
	
	
	public static String generateSQLClause(List<ParamCriteria> pcList, String cs, String cl, long offset,
			long limit){
		
		StringBuilder sb=new StringBuilder(" from ");

		sb.append(cs);
		sb.append(".");
		sb.append(cl);
		sb.append(" ");
		
		//generate where clause
		if(pcList.size()>0){
			sb.append(" where ");
			for(int i=0;i<pcList.size();i++){
				ParamCriteria pc=pcList.get(i);
				sb.append(pc.getCriteria());
				if(i!=pcList.size()-1) sb.append(" and ");
			}
		}
		//set limit
		sb.append(" limit ").append(limit);
		//set offset
		sb.append(" offset ").append(offset);
		

		
		System.out.println("### SQL Clause ###:"+sb.toString());
		
		return sb.toString();
	}
	
	
	
	

	
	
}
