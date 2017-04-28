package bigdata.service.sequoia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;

import static bigdata.util.SequoiaUtil.*;
import bigdata.dao.sequoia.SequoiadbDao;
import bigdata.model.QueryFieldDef;
import bigdata.util.DateUtil;
import bigdata.util.Page;
import bigdata.util.PageUtil;
import bigdata.util.ParamCriteria;
import bigdata.util.SequoiaUtil;
import bigdata.util.TimeRange;
import bigdata.util.TimeSlice;
import bigdata.util.PageUtil.PageAction;

@Service
public class Sequoiadb2ServiceImpl implements SequoiadbService {
	
	@Resource(name="sequoiadb2DaoImpl")
	private SequoiadbDao sdbDao;


	@Override
	public Page findAsPage(String cs, String cl,List<String> fields, List<ParamCriteria> pcList, Page page) throws BaseException, InterruptedException {
		// TODO Auto-generated method stub
		//
		long offset=PageUtil.getOffset(page);
		long limit=PageUtil.getLimit(page);
		
		String sqlClause=SequoiaUtil.generateSQLClause(pcList, cs, cl, offset, limit);
		BasicBSONList result=sdbDao.findAsPage(fields, sqlClause);
		page.setResult(result);
		
		if(page.getAction()==PageAction.fresh){
			String noLimtAndOffsetSqlClause=sqlClause.substring(0,sqlClause.indexOf("limit"));
			long count=sdbDao.findCount(noLimtAndOffsetSqlClause);
			//System.out.println(">>> TotalCount <<<::"+count);
			page.setTotalCount(count);
			page.setPageIndex(1);
			page.setTotalPage(PageUtil.computeTotalPage(page.getPageSize(), count));
			
		}else{
			page.setPageIndex(PageUtil.getNewPageIndex(page));
		}

		
		return page;
	}


}
