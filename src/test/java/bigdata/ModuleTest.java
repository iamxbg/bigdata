//package bigdata;
////package bigdata;
////
////import java.util.ArrayList;
////import java.util.List;
////import org.bson.BSONObject;
////import org.junit.Assert;
////import org.junit.Test;
////import org.junit.runner.RunWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.test.context.ContextConfiguration;
////import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
////
////import com.sequoiadb.exception.BaseException;
////
////import bigdata.config.RootConfig;
////import bigdata.config.SequoiadbConfig;
////import bigdata.model.Module;
////import bigdata.model.QueryFieldDef;
////import bigdata.service.sequoia.sys.ModuleService;
////
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(classes={RootConfig.class,SequoiadbConfig.class})
////public class ModuleTest {
////	
////	@Autowired
////	private ModuleService modService;
////	
////	
////	//@Test
////	public void testSetQueryFieldDefs() throws BaseException, InterruptedException{
////
////		
////		String modId="580f1f81146720af5c581818";
////		
////		QueryFieldDef def1=new QueryFieldDef("SN", "SN", "string", "SN", "=");
////		QueryFieldDef def2=new QueryFieldDef("TS", "TS", "string", "TS", "=");
////		QueryFieldDef def3=new QueryFieldDef("log_date", "開始時間", "timestamp", "start_time", ">=");
////		QueryFieldDef def4=new QueryFieldDef("log_date", "結束時間", "timestamp", "end_time", "<=");
////
////		
////
////		
////		List<QueryFieldDef> queryFieldDefs=new ArrayList<>();
////			queryFieldDefs.add(def1);
////			queryFieldDefs.add(def2);
////			queryFieldDefs.add(def3);
////			queryFieldDefs.add(def4);
////
////				
////		modService.setQueryFieldDefs(modId, queryFieldDefs);
////		
////	}
////	
////	
////	//@Test
////	public void testFindAll() throws BaseException, InterruptedException{
////		List<Module> modList=modService.findAll();
////		System.out.println("Size::"+modList.size());
////		for(Module mod:modList){
////			System.out.println("module'name::"+mod.getName());
////		}
////	}
////	
////	//@Test
////	public void testAddModule() throws BaseException, InterruptedException {
////		String name="cdbbc.dcslog";
////		String cs="cdbbc";
////		String cl="dcslog";
////		String desc="Bobcat日誌監控";
////		
////		Module mod=new Module(null, name, cs, cl, desc);
////		modService.add(mod);
////
////	}
////	
////	//@Test
////	public void testUpdateModule() throws BaseException, InterruptedException{
////		String _id="580ea54c14679f6f3a80a309";
////		String name="dcbbc.dcslog";
////		String cs="dcbbc";
////		String cl="dcslog";
////		String description="Bobcat日誌監控";
////		Module mod=new Module(_id,name, cs, cl, description);
////		modService.update(mod);
////	}
////	
////	//@Test
////	public void test(){
////		System.out.println(":: count"+((6-1)/5+1));
////	}
////	
////	@Test
////	public void testFindById() throws BaseException, InterruptedException{
////		String _id="580f1f81146720af5c581818";
////		Module module=modService.findById(_id);
////		BSONObject modBSON=Module.parseToBSON(module);
////		System.out.println(modBSON);
////		Assert.assertNotNull(module);
////	}
////	
////	
////}
