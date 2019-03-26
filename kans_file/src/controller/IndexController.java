package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.user.IUserService;
import vo.Menus;
import vo.PreMenus;
import vo.UserMenuVo;
import vo.UserVo;

@Controller
@RequestMapping("/index")
public class IndexController {
	protected static Logger logger = Logger.getLogger("controller");

	// @Autowired
	// private IUserDao userDao;

	@Autowired
	private IUserService userService;

	/**
	 * 跳转到index页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getCommonPage(HttpServletRequest hsr) {
		logger.debug("index page");

		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		hsr.setAttribute("userName", user.getUsername());
		// 获得当前用户的操作权限
		
		List<UserMenuVo> menuList = userService.getMenu(user.getId(), 1, 2, 2);
		
		UserMenuVo dbMenuVo = null;
		Boolean showFlag = false;
		if(menuList!=null && menuList.size()>0) {
			dbMenuVo = menuList.get(0);
			if(StringUtils.isNotBlank(dbMenuVo.getTitle()) && dbMenuVo.getTitle().equals("打包下载")) {
				showFlag = true;
			}
		}
		
		hsr.setAttribute("showFlag",showFlag);
		
		return "/index/index";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMenuList() {

		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserMenuVo> menuList = userService.getMenu(user.getId(), 1, 1, null);

		Map<String, Object> m = new HashMap<String, Object>();
		List<Menus> ml = new ArrayList<Menus>();
		List<PreMenus> ml2 = new ArrayList<PreMenus>();

		for (int i = 0; i < menuList.size(); i++) {
			List<UserMenuVo> menu2 = userService.getMenu(user.getId(), 1, 2, menuList.get(i).getId());

			// List<List<UserMenuVo>> llu = new ArrayList<List<UserMenuVo>>();
			// List<UserMenuVo> lu = new ArrayList<UserMenuVo>();
			// lu.add(new UserMenuVo());
			// llu.add(lu);

			// for (UserMenuVo umv : menu2) {
			// List<UserMenuVo> menu3 = userService.getMenu(user.getId(), 1, 3,
			// umv.getId());
			//
			// Menus menu = new Menus();
			//
			// // 封装菜单
			// menu.setMenuList(menu3);
			// menu.setUmv(umv);
			// ml.add(menu);
			// }

			for (UserMenuVo umv : menu2) {
				List<UserMenuVo> menu3 = userService.getMenu(user.getId(), 1, 3, umv.getId());
				umv.setMenuList(menu3);
			}

			// PreMenus pm = new PreMenus();
			// pm.setMenuList(ml);
			// pm.setUmv(menuList.get(i));
			// ml2.add(pm);

			Menus menu = new Menus();

			// 封装菜单
			// menu.setMenuList(llu);
			menu.setMenuList(menu2);
			menu.setUmv(menuList.get(i));
			ml.add(menu);

		}

		m.put("menus", ml);

		return m;
	}
}