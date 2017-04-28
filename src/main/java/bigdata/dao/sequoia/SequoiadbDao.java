package bigdata.dao.sequoia;

import java.util.List;
import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.types.BasicBSONList;

import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;
import bigdata.util.Page;
import bigdata.util.ParamCriteria;
import bigdata.util.SequoiaUtil.QueryType;
import bigdata.util.TimeSlice;

public interface SequoiadbDao {

	public long findCount(String sqlClause) throws BaseException,InterruptedException;
	
	public BasicBSONList findAsPage(List<String> fields,String sqlClause) throws BaseException,InterruptedException;
	
	
	
}
