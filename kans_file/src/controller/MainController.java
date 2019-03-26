package controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pojo.UserTable;
import dao.IUserDao;

@Controller
@RequestMapping("/main")
public class MainController {
	protected static Logger logger = Logger.getLogger("controller");
	
	

	@Autowired
	private IUserDao userDao;
	

	/**
	 * 跳转到commonpage页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/common", method = RequestMethod.GET)
	public String getCommonPage() {
		logger.debug("Received request to show common page");
		
//		UserTable ut = new UserTable();
//		ut.setId(1);
//		
//		List<UserTable> userTableList = userDao.getUsers(ut);
//		
//		for(UserTable userTable:userTableList){
//			
//			System.out.println("得到手机号码:"+userTable.getUserName());
//		}
		
		System.out.println("-----------");
		
		return "/login/commonpage";
	}

	/**
	 * 跳转到adminpage页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getAadminPage() {
		logger.debug("Received request to show admin page");
		return "/login/adminpage";

	}

}
