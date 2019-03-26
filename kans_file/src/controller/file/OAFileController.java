package controller.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.pagehelper.PageHelper;
import pojo.FilePath;
import pojo.Role;
import service.file.IOAFileService;
import service.materiel.IMaterielService;
import vo.PageInfo;

@Controller
@RequestMapping({ "/oaFile" })
public class OAFileController {
	
	public static Logger logger = Logger.getLogger(FileController.class);
	
	@Autowired
	private IOAFileService oaFileService;
	
	
	@RequestMapping(value = { "/getList" })
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "5") Integer rows // 页数大小
	) {

		PageHelper.startPage(page, rows);
		List<FilePath> filePathList = oaFileService.getList();
		PageInfo<FilePath> p = new PageInfo<FilePath>(filePathList);

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
	@RequestMapping(value = { "/saveInfo" })
	public void saveRole(HttpServletRequest request, HttpServletResponse response) {

		
		FilePath filePath = new FilePath();		
		filePath.setType(request.getParameter("type"));
		filePath.setPath(request.getParameter("path"));
		filePath.setDescription(request.getParameter("description"));
		
		String id = request.getParameter("id");
		if(id == null|| id == "") {
			oaFileService.addFilePath(filePath);
		}else {
			filePath.setId(Integer.valueOf(id));
			oaFileService.updateFilePath(filePath);
		}
	}
	
	
	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/delInfo" })
	public void delInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("idStr") String idStr) {
		String[] idList = idStr.split(",");
		for (String id : idList) {
			oaFileService.delFilePath(Integer.parseInt(id));
		}
	}
}
