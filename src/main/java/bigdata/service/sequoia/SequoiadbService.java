package bigdata.service.sequoia;

import java.util.List;
import java.util.Map;

import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.types.BasicBSONList;

import com.sequoiadb.base.DBQuery;
import com.sequoiadb.exception.BaseException;

import bigdata.model.QueryFieldDef;
import bigdata.util.Page;
import bigdata.util.ParamCriteria;
import bigdata.util.TimeRange;

public interface SequoiadbService {

	
	
	public Page findAsPage(String cs,String cl,List<String> fields,List<ParamCriteria> pcList,Page page) throws BaseException, InterruptedException ;

	//public BSONObject findAggregate(String sql);
	
	
}
