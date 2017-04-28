package bigdata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

public class User implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private String _id;
	private String username;
	private String password;

	
	
	private List<Module> modules;
	
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}


	public User( String username, String password) {
		super();
		this.username = username;
		this.password = password;

	}




	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	/*
	public static User parseToPOJO(BSONObject bson){
		User user=new User(bson.get("_id").toString()
				, bson.get("name").toString()
				, bson.get("username").toString()
				,bson.get("password").toString()
				, bson.get("work_id").toString());
		
		List<Module> modList=new ArrayList<>();
			BasicBSONList list=(BasicBSONList) bson.get("modules");
			// change to  java 8 Stream method
			for(int i=0;i<list.size();i++){
				BSONObject modBSON=(BSONObject) list.get(i);
				Module mod=Module.parseToPOJO(modBSON);
				modList.add(mod);
			}
			// set Module Info
			user.setModules(modList);
			
		return user;
	}
	
	public static BSONObject parseToBSON(User user){
		BSONObject userBSON=new BasicBSONObject();
			userBSON.put("password", user.getPassword());
			userBSON.put("name", user.getName());
			userBSON.put("work_id", user.getWork_id());
			userBSON.put("username", user.getUsername());
			userBSON.put("_id", user.get_id());
			
			// set modules  , change to java 8 flavor.
			
			BasicBSONList modBSONList=new BasicBSONList();
			if(user.getModules()!=null){
				for(Module m:user.getModules()){
					BSONObject mBSON=Module.parseToBSON(m);
					modBSONList.add(mBSON);
				}
				userBSON.put("modules", modBSONList);
			}
			
			
			return userBSON;
	}
	*/
}
