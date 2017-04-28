package bigdata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("module")
public class Module implements Serializable{

	private static final long serialVersionUID = 1L;
	
		private String _id;
		private String name;
		private String cs;
		private String cl;
		private String desc;
		private List<QueryFieldDef>  qfdList;
		
		public Module() {
			// TODO Auto-generated constructor stub
		}

	
		
		
		public Module(String _id, String name, String cs, String cl, String desc, List<QueryFieldDef> qfdList) {
			super();
			this._id = _id;
			this.name = name;
			this.cs = cs;
			this.cl = cl;
			this.desc = desc;
			this.qfdList = qfdList;
		}




		public Module(String _id, String name, String cs, String cl, String desc) {
			super();
			this._id = _id;
			this.name = name;
			this.cs = cs;
			this.cl = cl;
			this.desc = desc;
		}




		public static BSONObject parseToBSON(Module module){
			BSONObject modBSON=new BasicBSONObject();
				modBSON.put("_id", module.get_id());
				modBSON.put("cl", module.getCl());
				modBSON.put("cs", module.getCs());
				modBSON.put("desc", module.getDesc());
				modBSON.put("name", module.getName());
				
				BasicBSONList qfBsonList =new BasicBSONList();
				List<QueryFieldDef> qfList=module.getQfdList();
				if(qfList!=null){

					for(int i=0;i<qfList.size();i++){
						QueryFieldDef qf=qfList.get(i);
						BSONObject qfBSON=new BasicBSONObject();
							qfBSON.put("label", qf.getLabel());
							qfBSON.put("desc", qf.getDesc());
							qfBSON.put("type", qf.getType());
							qfBSON.put("field", qf.getField());
							qfBSON.put("opr", qf.getOperator());
			
						qfBsonList.add(qfBSON);
					}
				}
				
				modBSON.put("queryFieldDefs", qfBsonList);
				
				return modBSON;
		}
		
		public static Module parseToPOJO(BSONObject bson){

			Module module=new Module(bson.get("_id").toString()
								, bson.get("name").toString()
								, bson.get("cs").toString()
								, bson.get("cl").toString()
								, bson.get("desc").toString());
		
				List<QueryFieldDef> qfdList=new ArrayList<>();
				
				BasicBSONList qfdBSONList=(BasicBSONList) bson.get("queryFieldDefs");
				
				if(qfdBSONList!=null){
					for(int i=0;i<qfdBSONList.size();i++){
						BSONObject qfdBSON=(BSONObject) qfdBSONList.get(i);
						QueryFieldDef qfd=new QueryFieldDef(qfdBSON.get("field").toString()
								, qfdBSON.get("desc").toString()
								,  qfdBSON.get("type").toString()
								,  qfdBSON.get("label").toString()
								,  qfdBSON.get("opr").toString());
						
					qfdList.add(qfd);
					}
				}
				
				module.setQfdList(qfdList);
				
				return module;
		}




		public String get_id() {
			return _id;
		}




		public void set_id(String _id) {
			this._id = _id;
		}




		public String getName() {
			return name;
		}




		public void setName(String name) {
			this.name = name;
		}




		public String getCs() {
			return cs;
		}




		public void setCs(String cs) {
			this.cs = cs;
		}




		public String getCl() {
			return cl;
		}




		public void setCl(String cl) {
			this.cl = cl;
		}




		public String getDesc() {
			return desc;
		}




		public void setDesc(String desc) {
			this.desc = desc;
		}




		public List<QueryFieldDef> getQfdList() {
			return qfdList;
		}




		public void setQfdList(List<QueryFieldDef> qfdList) {
			this.qfdList = qfdList;
		}
		
		
		
}
