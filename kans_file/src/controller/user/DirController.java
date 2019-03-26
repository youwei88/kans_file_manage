package controller.user;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.pagehelper.PageHelper;
import pojo.Operation;
import service.file.IFileService;
import service.user.IRoleService;
import vo.PageInfo;

@Controller
@RequestMapping({ "/dir" })
public class DirController {
	protected static Logger logger = Logger.getLogger(DirController.class);

	private static final int normalStatus = 1;

	@Autowired
	private IFileService fileService;

	@Autowired
	private IRoleService roleService;

	// 文件根目录
	@Value("#{config.RootFilePath}")
	private String RootFilePath;

	/**
	 * 目录列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/dirList" })
	@ResponseBody
	public Map<String, Object> dirList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "5") Integer rows // 页数大小
	) {

		PageHelper.startPage(page, rows);
		List<Operation> operList = roleService.getAllDirs();
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
	 * 添加目录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/addDir" })
	public void addDir(HttpServletRequest request, HttpServletResponse response) {

		Operation operation = new Operation();
		// 入库
		String title = request.getParameter("name");
		int nextId = fileService.getOperNext();
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		int isFromSap = Integer.parseInt(request.getParameter("isFromSap"));
		String operName = request.getParameter("operName");
		String path = request.getParameter("path"); // "Z006(料体)/test"
		
		if(path.startsWith("/")) {
			// 根目录的查看下载权限
			operation.setId(nextId);
			operation.setTitle(title);
			operation.setOperName(operName);
			operation.setType(3);
			operation.setParentName(parentId);
			operation.setStatus(normalStatus);
			operation.setIsFromSap(isFromSap);
			operation.setOptionStatus(1);
			fileService.addDir(operation);
			
			// 根目录的上传删除权限
			Operation operation2 = new Operation();
			operation2.setId(nextId);
			operation2.setTitle(title);
			operation2.setOperName(operation.getId()+"");
			operation2.setType(3);
			operation2.setParentName(fileService.getIdByOperName(String.valueOf(parentId)) == null ? 32:fileService.getIdByOperName(String.valueOf(parentId)));
			operation2.setStatus(normalStatus);
			operation.setIsFromSap(isFromSap);
			operation.setOptionStatus(0);
			fileService.addDir(operation2);
			
			new File(RootFilePath + "/" + title, path.substring(path.indexOf("/"))).mkdirs();
		}else {
			operation.setId(nextId);
			operation.setTitle(title);
			operation.setParentName(parentId);
			operation.setStatus(normalStatus);
			operation.setIsFromSap(isFromSap);
			operation.setOptionStatus(0);
			fileService.addDir(operation);
			
			// 上传下载的权限
			Operation operation2 = new Operation();
			operation2.setTitle(title);
			operation2.setOperName(String.valueOf(nextId));
			operation2.setParentName(fileService.getIdByOperName(String.valueOf(parentId)));
			operation2.setStatus(normalStatus);
			operation2.setIsFromSap(0);
			operation.setOptionStatus(0);
			fileService.addDir(operation2);

			
			// parentId = 16, name = test2, path = Z006(料体)/test2
			
			// 248  "国外目录" "Z00A(宣称原料)/国外目录"
			// 创建目录
			File targetFile = new File(RootFilePath);
			for (String name : targetFile.list()) {
				// 如果父目录是对应的数字开头，则建目录
				if (name.startsWith(path.substring(3, 4))) {
					new File(RootFilePath + "/" + name, path.substring(path.indexOf("/"))).mkdirs();
				}
			}
		}
		
	}

	public static void main(String[] args) {
		
		String path ="/";
		String path1 = "12321/234";
		
		System.out.println(path.startsWith("path1"));
/*		File targetFile = new File("D:\\home");
		for (String name : targetFile.list()) {
			System.out.println(name);
		}*/
/*		String path = "Z006(料体)/test";
		System.out.print(path.substring(3, 4));*/
	}
	
	/**
	 * 删除目录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = { "/delDir" })
	public void delDir(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id,
			@RequestParam("path") String path) throws IOException {
		if (path.indexOf("/") != -1) {
			File targetFile = new File(RootFilePath);
			for (String name : targetFile.list()) {
				// 遍历父目录是对应的数字开头
				if (name.startsWith(path.substring(3, 4))) {
					File file = new File(RootFilePath + "/" + name, path.substring(path.indexOf("/")));
					if (null != file.list() && 0 != file.list().length) {
						// 返回2，代表目录下有文件，需要先删除文件
						response.getWriter().write("2");
						return;
					} else {
						file.delete();
					}
				}
			}
		}
		roleService.delOperation(Integer.parseInt(id));
		roleService.delRoleOperationByOperId(Integer.parseInt(id));
		
		// 上传下载权限对应的id
		int upId = fileService.getIdByOperName(String.valueOf(Integer.parseInt(id)));
		roleService.delOperation(upId);
		roleService.delRoleOperationByOperId(upId);
	}

	/**
	 * 应用到目录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/applyDir" })
	public void applyDir(HttpServletRequest request, HttpServletResponse response) {

		fileService.applyDir(RootFilePath);

	}
}