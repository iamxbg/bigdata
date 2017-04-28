package bigdata.model;

import java.io.Serializable;

import bigdata.util.ParamCriteria;

public class QueryField implements Serializable{


	private static final long serialVersionUID = 1L;
	private Object value;
	private QueryFieldDef def;
	
	public QueryField(Object value, QueryFieldDef def) {
		super();
		this.value = value;
		this.def = def;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public QueryFieldDef getDef() {
		return def;
	}

	public void setDef(QueryFieldDef def) {
		this.def = def;
	}

	public ParamCriteria getParamCriteria(){
		ParamCriteria pc=new ParamCriteria(def.getField(), this.value,def.getOperator());
		return pc;
	}

	
	
	

	
}
