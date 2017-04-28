package bigdata.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimeSlice {
	
		private Date start;
		private Date end;
		
		private static SimpleDateFormat defaultDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		
		
		public TimeSlice() {
			// TODO Auto-generated constructor stub
		}
		
		public TimeSlice(Date start, Date end) {
			super();
			this.start = start;
			this.end = end;
		}

		public Date getStart() {
			return start;
		}
	
		public void setStart(Date start) {
			this.start = start;
		}
	
		public Date getEnd() {
			return end;
		}
	
		public void setEnd(Date end) {
			this.end = end;
		}
		
		public String getStartTimeStr(){
			return defaultDateFormat.format(this.start);
		}
		
		public String getEndTimeStr(){
			return defaultDateFormat.format(this.end);
		}

		
	}

	

