package controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pojo.UserTable;
import service.user.IUserService;
import vo.PageInfo;
import vo.UserTableVo;

import common.pagehelper.PageHelper;
import common.util.DateUtil;

@Controller
@RequestMapping({"/userList"})
public class UserListController
{
  protected static Logger logger = Logger.getLogger(UserListController.class);

//  @Autowired
//  private IUserDao userDao;
  
  @Autowired
  private IUserService userService;

  @RequestMapping(value="select")
  public String selectUser(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") Integer pageNum,
			@RequestParam(required=false, defaultValue="10") Integer pageSize,
			UserTableVo userTableVo)
  {
    try
    {
    	
//        UserTable ut = new UserTable();

//      ut.setUserName("183180手机号");
//      ut.setUserPassWord("abc123");
//      ut.setSourceUrl("2");
//      ut.setUserPhone("18318059593");
//      	
//      ut.setCreatTime(DateUtil.parseDateString("2016-12-12", "yyyy-MM-dd"));
    	userTableVo.setCreatTime(DateUtil.parseDateString(userTableVo.getCreatTimeStr(), "yyyy-MM-dd"));
    	userTableVo.setEndTime(DateUtil.parseDateString(userTableVo.getEndTimeStr(), "yyyy-MM-dd"));
      	
	      PageHelper.startPage(pageNum, pageSize);
//	      List<UserTable> userTableList = userService.getUsers(userTableVo);
	      List<UserTable> userTableList = null;
	      PageInfo<UserTable> page = new PageInfo<UserTable>(userTableList);
	      
	      System.out.println(page);
	      logger.info("------------后台用户分页查询成功!!!");
	      
	      request.setAttribute("userTableVo", userTableVo);
	      request.setAttribute("page", page);
	      return "/user/userList";
    }
    catch (Exception e)
    {
    	logger.info("后台用户分页查询失败!!!");
    }
    return "/login/fail";
  }
  
  
}