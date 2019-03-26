package controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.pagehelper.PageHelper;
import common.util.StringUtils;
import pojo.Role;
import service.user.IRoleService;
import service.user.IUserService;
import vo.PageInfo;

@Controller
@RequestMapping({ "/role" })
public class RoleController {
	protected static Logger logger = Logger.getLogger(RoleController.class);

	private static final int normalStatus = 1;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@RequestMapping(value = { "/index" }, method = { RequestMethod.GET })
	public String saveUser() {
		try {

			logger.debug("首页");
			return "/user/role";
		} catch (Exception e) {
		}
		return "fail";
	}

	@RequestMapping(value = { "/roleList" })
	@ResponseBody
	public Map<String, Object> getRoleList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "5") Integer rows // 页数大小
	) {

		PageHelper.startPage(page, rows);
		List<Role> roleList = userService.getRoles(null, normalStatus);
		PageInfo<Role> p = new PageInfo<Role>(roleList);

		Map<String, Object> m = new HashMap<String, Object>();

		m.put("total", p.getTotal());
		m.put("rows", p.getRows());

		return m;
	}

	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/saveRole" })
	public void saveRole(HttpServletRequest request, HttpServletResponse response) {

		Role role = new Role();
		role.setRoleName(request.getParameter("roleName"));
		role.setStatus(1);//0:未启用,1:启用
		role.setMark(request.getParameter("mark"));
		
		String id = request.getParameter("id");
		
		if(id == null|| id == "") {
			roleService.addRole(role);
		}else {
			role.setId(Integer.valueOf(id));
			roleService.updateRole(role);
		}
		
		

	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/delRole" })
	public void delRole(HttpServletRequest request, HttpServletResponse response, @RequestParam("idStr") String idStr) {
		String[] idList = idStr.split(",");
		for (String id : idList) {
			roleService.delRole(Integer.parseInt(id));
			roleService.delRoleOperation(Integer.parseInt(id));
		}
	}

	/**
	 * 呼出选择角色对话框
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/editRoleOperation" })
	public String editRoleOperation(HttpServletRequest request, HttpServletResponse response) {

		List<Role> roleList = userService.getRoles(null, normalStatus);

		request.setAttribute("roleList", roleList);

		return "/user/edit_role_operation";
	}

	/**
	 * 选择角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/selectRole" })
	public String selectRole(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("roleId", request.getParameter("roleId"));

		return "/user/opertion";
	}

	/**
	 * 更改角色权限
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/modifyRoleOperation" })
	public String editRole(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("roleid") String roleid, @RequestParam("idStr") String idStr) {

		if (StringUtils.isNotEmpty(roleid) && StringUtils.isNotEmpty(idStr)) {
			// 先删除角色原有权限
			int rlId = Integer.parseInt(roleid);
			roleService.delRoleOperation(rlId);

			// 再批量插入新的权限
			// String[] opers = idStr.split(",");
			// List<Integer> operIds = new ArrayList<Integer>();
			// for (String s : opers) {
			// operIds.add(Integer.parseInt(s));
			// }
			// roleService.addRoleOperation(roleId, operIds);
			for (String s : idStr.split(",")) {
				roleService.addRoleOperation(rlId, Integer.parseInt(s));
			}

		}

		return "/user/opertion";
	}
}