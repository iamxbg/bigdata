package bigdata.util;

import org.springframework.stereotype.Component;

@Component
public class PageUtil {
	
	public static  enum PageAction{
		first,last,next,prev,fresh
	}
	
	public static PageAction valueFrom(String acitonStr){
		PageAction action=null;
		switch(acitonStr){
		case "first":action=PageAction.first;break;
		case "next": action=PageAction.next;break;
		case "prev": action=PageAction.prev;break;
		case "last":action=PageAction.last;break;
		case "fresh":action=PageAction.fresh;break;
		default: break;
		}
		return action;
		
	}
	
	public static int computeTotalPage(int pageSize,long totalCount){
		return (int) ((totalCount-1)/pageSize+1);
	}
	
	public static long getLimit(Page page){
		return page.getPageSize();
	}
	
	public static  long getOffset(Page page){
		int pageIndex=page.getPageIndex();
		int pageSize=page.getPageSize();
		int totalPage=page.getTotalPage();

		
		PageAction which=page.getAction();
		long offset=0;
		switch(which){
		case first:
			break;
		case prev:
				System.out.println("pageIndex::"+pageIndex);
				offset=(pageIndex>2)?((pageIndex-2)*pageSize):0;
				System.out.println("offset::"+offset);
			break;
		case next:
				offset=(pageIndex<totalPage)?(pageIndex*pageSize):(totalPage-1)*pageSize;
			break;
		case last:
				offset=(totalPage-1)*pageSize;
			break;
		case fresh:
				offset=0;
			break;
			default:
				break;
		}
		
		return offset;
	}
	
	public static int getNewPageIndex(Page page){
		int pageIndex=page.getPageIndex();
		int totalPage=page.getTotalPage();

		PageAction which=page.getAction();
		int index=0;
		switch(which){
		case first:
				index=1;
			break;
		case prev:
				index=(pageIndex>1)?(pageIndex-1):1;
			break;
		case next:
				index=(pageIndex<totalPage)?(pageIndex+1):totalPage;
			break;
		case last:
				index=totalPage;
			break;
		case fresh:
				index=1;
			break;
			default:
				break;
		}
		
		return index;
	}
}
