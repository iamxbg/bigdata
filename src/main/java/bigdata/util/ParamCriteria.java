package bigdata.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author C3406498
 *
 */
public class ParamCriteria {
	
	private String key;
	private Object value;
	private String operator;
	
	public ParamCriteria(String key, Object value, String operator) {
		super();
		this.key = key;
		this.value = value;
		this.operator = operator;
	}
	
	
	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getCriteria(){
		return new StringBuilder(key)
					.append(' ')
					.append(operator)
					.append(" '")
					.append(value)
					.append("' ").toString();
	}
	
	public static Map<String, Object> FilterParam(Map<String, Object> map,String... keys){
		Map<String, Object> filterd=new HashMap<String, Object>();
		for(String key:map.keySet()){
			for(int i=0;i<keys.length;i++){
				if(key.equals(keys[i])){
					filterd.put(key, map.get(key));
					break;
				}
			}
		}
		return filterd;
	}
	
}