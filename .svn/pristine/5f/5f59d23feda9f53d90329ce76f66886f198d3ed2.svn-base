package controller.operation;

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
import pojo.Operation;
import service.user.IRoleService;
import service.user.IUserService;
import vo.PageInfo;

@Controller
@RequestMapping({ "/operation" })
public class OperationController {
	protected static Logger logger = Logger.getLogger(OperationController.class);

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

	/**
	 * 权限列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/operationList" })
	@ResponseBody
	public Map<String, Object> operationList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "5") Integer rows // 页数大小
	) {

		PageHelper.startPage(page, rows);
		List<Operation> operList = roleService.getAllOperations();
		for (Operation op : operList) {
			op.setDisplayParentName(roleService.getParentName(op.getParentName()));
		}
		PageInfo<Operation> p = new PageInfo<Operation>(operList);

		Map<String, Object> m = new HashMap<String, Object>();

		m.put("total", p.getTotal());
		m.put("rows", p.getRows());

		return m;
	}

	/**
	 * 添加权限
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/addOperation" })
	public void addRole(HttpServletRequest request, HttpServletResponse response) {

		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			updateOperation(request);
		} else {
			Operation operation = new Operation();
			operation.setTitle(request.getParameter("title"));
			operation.setOperName(request.getParameter("operName"));
			operation.setType(Integer.parseInt(request.getParameter("type")));
			operation.setParentName(Integer.parseInt(request.getParameter("parentName")));
			operation.setOrderNum(Integer.parseInt(request.getParameter("orderNum")));
			operation.setFlagName(request.getParameter("flagName"));
			operation.setStatus(Integer.parseInt((request.getParameter("status"))));
			operation.setCode("0");
			roleService.addOperation(operation);
		}
	}

	/**
	 * 删除权限
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/delOperation" })
	public void delOperation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("idStr") String idStr) {
		String[] idList = idStr.split(",");
		for (String id : idList) {
			roleService.delOperation(Integer.parseInt(id));
			roleService.delRoleOperationByOperId(Integer.parseInt(id));
		}
	}

	// 修改权限
	public void updateOperation(HttpServletRequest request) {
		Operation operation = new Operation();

		operation.setId(Integer.parseInt((request.getParameter("id"))));
		operation.setTitle(request.getParameter("title"));
		operation.setOperName(request.getParameter("operName"));
		operation.setFlagName(request.getParameter("flagName"));
		roleService.updateOperation(operation);
	}
}