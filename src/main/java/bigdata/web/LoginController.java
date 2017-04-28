package bigdata.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.DBQuery;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.base.SequoiadbDatasource;
import com.sequoiadb.exception.BaseException;
import bigdata.model.Module;
import bigdata.model.User;

@Controller
@RequestMapping(path="/login")
public class LoginController {

	
	private static final Logger logger=LogManager.getLogger(LoginController.class);
	
	@RequestMapping(path="/desktop",method=RequestMethod.POST)
	public String login(@RequestParam(name="username") String username
						,@RequestParam(name="password") String password
						,Model model
						,HttpServletRequest req) throws BaseException, InterruptedException{

		
		
		if("bobcat".equals(username) && "test123".equals(password)){
			
			System.out.println("to Desktop");
			
			return "/desktop.jsp";
		}else{
			return "redirect:/index.jsp";
		}

		
	}
	

	
	
}
