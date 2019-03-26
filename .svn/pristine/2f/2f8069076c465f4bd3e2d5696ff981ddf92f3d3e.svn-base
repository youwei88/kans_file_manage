package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.util.DateUtil;
import common.util.Tools;

import pojo.UserTable;
import service.user.IUserService;

import vo.UserTableVo;

@Controller
@RequestMapping("/poi")
public class PoiController {
	
	
	  @Autowired
	  private IUserService userService;
	  
	  protected static Logger logger = Logger.getLogger(PoiController.class);
	
	@RequestMapping(value = "/creatExel")
	public void creatExel(HttpServletResponse response,UserTableVo userTableVo){
		
		userTableVo.setCreatTime(DateUtil.parseDateString(userTableVo.getCreatTimeStr(), "yyyy-MM-dd"));
    	userTableVo.setEndTime(DateUtil.parseDateString(userTableVo.getEndTimeStr(), "yyyy-MM-dd"));
		
//		List<UserTable> userTableList = userService.getUsers(userTableVo);
		List<UserTable> userTableList = null;
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("导出数据");
			HSSFCellStyle style = wb.createCellStyle(); // 样式对象

			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			
			//行数
			HSSFRow row = sheet.createRow((short) 0);
			HSSFCell cell = row.createCell((short) (0));
			cell.setCellValue("id");
			cell.setCellStyle(style);
			cell = row.createCell((short) (1));
			cell.setCellValue("手机号");
			cell.setCellStyle(style);
			cell = row.createCell((short) (2));
			cell.setCellValue("交易机构");
			cell.setCellStyle(style);
			cell = row.createCell((short) (3));
			cell.setCellValue("渠道号");
			cell.setCellStyle(style);
			cell = row.createCell((short) (4));
			cell.setCellValue("呈现页面");
			cell.setCellStyle(style);
			cell = row.createCell((short) (5));
			cell.setCellValue("来源端");
			cell.setCellStyle(style);
			cell = row.createCell((short) (6));
			cell.setCellValue("创建时间");
			cell.setCellStyle(style);
			
			
			for(int i = 0; i<userTableList.size(); i++){
				
				HSSFRow row2 = sheet.createRow((short) 1+i);
				
				HSSFCell cell1 = row2.createCell((short) (0));
				cell1.setCellValue(userTableList.get(i).getId());
				cell1.setCellStyle(style);
				
				cell1 = row2.createCell((short) (1));
				cell1.setCellValue(userTableList.get(i).getUserPhone());
				cell1.setCellStyle(style);
				
				cell1 = row2.createCell((short) (2));
				cell1.setCellValue(userTableList.get(i).getSourceUrl());
				cell1.setCellStyle(style);
				
				cell1 = row2.createCell((short) (3));
				cell1.setCellValue(userTableList.get(i).getSourceType());
				cell1.setCellStyle(style);
				
				cell1 = row2.createCell((short) (4));
				cell1.setCellValue(userTableList.get(i).getPageType());
				cell1.setCellStyle(style);
				
				cell1 = row2.createCell((short) (5));
				cell1.setCellValue(userTableList.get(i).getAccessType());
				cell1.setCellStyle(style);
				
				cell1 = row2.createCell((short) (6));
				cell1.setCellValue(DateUtil.formatDate(userTableList.get(i).getCreatTime(), "yyyy-MM-dd HH:dd:ss"));
				cell1.setCellStyle(style);
			}
			
		
			
			  String fileName = "userTable表数据";
		      ByteArrayOutputStream os = new ByteArrayOutputStream();
		      wb.write(os);
		      byte[] content = os.toByteArray();
		      InputStream is = new ByteArrayInputStream(content);
		      // 设置response参数，可以打开下载页面
		      response.reset();
		      response.setContentType("application/vnd.ms-excel;charset=utf-8");
		      response.setHeader("Content-Disposition", "attachment;filename="
		          + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		      ServletOutputStream out = response.getOutputStream();
		      BufferedInputStream bis = null;
		      BufferedOutputStream bos = null;
		 
		      try {
		        bis = new BufferedInputStream(is);
		        bos = new BufferedOutputStream(out);
		        byte[] buff = new byte[2048];
		        int bytesRead;
		        // Simple read/write loop.
		        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		          bos.write(buff, 0, bytesRead);
		        }
		      } catch (Exception e) {
		        // TODO: handle exception
		        e.printStackTrace();
		      } finally {
		        if (bis != null)
		          bis.close();
		        if (bos != null)
		          bos.close();
		      }

		      logger.info("导出数据成功！！");
			
			System.out.print("OK");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("导出数据失败！！");
		}
		
	} 


}
