package controller.materiel;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.pagehelper.PageHelper;
import pojo.Materiel;
import pojo.Operation;
import service.file.IFileService;
import service.materiel.IMaterielService;
import service.user.IRoleService;
import vo.PageInfo;
import vo.UserVo;

@Controller
@RequestMapping({ "/materiel" })
public class MaterielController {
	protected static Logger logger = Logger.getLogger(MaterielController.class);

	@Autowired
	private IMaterielService materielService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IFileService fileService;

	// 文件根目录
	@Value("#{config.RootFilePath}")
	private String RootFilePath;

	/**
	 * 获取物料编码列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/materielList" })
	@ResponseBody
	public Map<String, Object> sapList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "100") Integer rows, // 页数大小
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "dscp", required = false) String dscp,
			@RequestParam(value = "isUserCreated", required = false) String isUserCreated) {

		PageHelper.startPage(page, rows);
		List<Materiel> saps = materielService.getMateriels(code, dscp ,isUserCreated);
		PageInfo<Materiel> p = new PageInfo<Materiel>(saps);

		Map<String, Object> m = new HashMap<String, Object>();

		m.put("total", p.getTotal());
		m.put("rows", p.getRows());

		return m;
	}

	/**
	 * 手动同步sap编码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/synchronizeMateriels" })
	//@ResponseBody
	public String synchronizeMateriels(HttpServletRequest request,
			@RequestParam(value = "start", required = true) String start,
			@RequestParam(value = "end", required = true) String end) {
		materielService.synchronizeMateriels(start, end);
		
		return "/user/result";
	}

	/**
	 * 添加物料
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/addMateriel" })
	public void addMateriel(HttpServletRequest request, HttpServletResponse response) {

		String code = request.getParameter("code");
		String isAppFlag = request.getParameter("isAppFlag");
		String dscp = request.getParameter("dscp");
		Materiel materiel = new Materiel();
		materiel.setCode(code);
		materiel.setType(request.getParameter("type"));
		materiel.setDscp(request.getParameter("dscp"));
		materiel.setGrp(dscp);
		materiel.setGrpdesc(request.getParameter("grpdesc"));
		materiel.setMark(request.getParameter("mark"));
		// 用户自建，可删除
		materiel.setIsUserCreated("1");
		materiel.setIsAppFlag(isAppFlag);
		materielService.synchronizeMateriels(materiel);
		
		if(isAppFlag!=null && ("1").equals(isAppFlag)) {
			System.out.println("新建用户菜单数据:"+isAppFlag);
			Operation operation = new Operation();
			operation.setTitle(dscp);
			operation.setOperName("/jsp/file/declareFile.jsp?type="+code);
			operation.setType(2);//二级菜单
			operation.setParentName(1);//父级菜单
			operation.setOrderNum((new BigDecimal(materielService.getMaxOrderNum()).add(BigDecimal.ONE)).intValue());
			operation.setFlagName("icon-set");
			operation.setStatus(1);
			operation.setIsFromSap(0);
			operation.setOptionStatus(0);
			operation.setCode(code);
			roleService.addOperation(operation);
		}

		// 建目录
		// 一级目录
		new File(RootFilePath, code).mkdirs();

		// 找到一级目录的权限id
		List<Operation> secDirs = materielService.findSonDir(0, "Z00" + code.substring(0, 1));
		for (Operation op : secDirs) {
			// 二级目录
			new File(RootFilePath + "/" + code, op.getTitle()).mkdirs();

			List<Operation> thrDirs = materielService.findSonDir(op.getId(), null);
			for (Operation op2 : thrDirs) {
				// 三级目录
				new File(RootFilePath + "/" + code + "/" + op.getTitle(), op2.getTitle()).mkdirs();
			}
		}
	}

	/**
	 * 删除物料
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/delMateriel" })
	public void delMateriel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("idStr") String idStr) {
		String[] idList = idStr.split(",");
		for (String id : idList) {
			//删除物料数据
			materielService.delMaterielById(id);
			//删除菜单数据
			
			//删除菜单关联数据
			Operation operation = fileService.getInfoBycode(id);
			if(operation != null) {				
				roleService.delRoleOperationByOperId(operation.getId());
				roleService.delOperation(operation.getId());
			}
			
			
		}
		
		//roleService.delRoleOperationByOperId(Integer.parseInt(id));
	}
	
	
	/**
	 * 获取物料操作权限
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getMaterialOperate" })
	@ResponseBody
	public List<Operation> getMaterialOperate() {
		
		List<Operation> materials = new ArrayList<Operation>();
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user!=null) {
			materials = roleService.getMaterialOperate(user.getId(), 6);
		}
		return materials;
	}
	
}