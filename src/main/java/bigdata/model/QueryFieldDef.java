package bigdata.model;

import java.io.Serializable;

public class QueryFieldDef implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private String field;
	private String desc;
	private String type;
	private String label;
	private String operator;
	
	
	
	public QueryFieldDef(String field, String desc, String type, String label, String operator) {
		super();
		this.field = field;
		this.desc = desc;
		this.type = type;
		this.label = label;
		this.operator = operator;
	}
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
}
