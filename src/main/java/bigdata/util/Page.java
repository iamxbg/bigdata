package bigdata.util;

import org.bson.BSONObject;
import org.bson.types.BasicBSONList;
import bigdata.util.PageUtil.PageAction;


public class Page {

	private int pageIndex=1;
	private int pageSize=10;
	private long totalCount=0;
	private int totalPage=0;
	
	private PageAction action=PageAction.fresh;
	
	private BasicBSONList result;
	
	
	public Page(int pageIndex, int pageSize, int totalPage,long totalCount, PageAction action) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage=totalPage;
		this.action = action;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public PageAction getAction() {
		return action;
	}

	public void setAction(PageAction action) {
		this.action = action;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public BasicBSONList getResult() {
		return result;
	}

	public void setResult(BasicBSONList result) {
		this.result = result;
	}

	
	
	

}
