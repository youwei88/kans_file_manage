package controller.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pojo.FileInfo;
import pojo.FileInfoBack;
import pojo.FilePath;
import pojo.OAFile;
import service.file.IFileBackService;
import service.file.IFilePathService;
import service.file.IFileService;

@Controller
@RequestMapping({ "/api" })
public class TransOAFileController {

	
	@Autowired
	private IFilePathService filePathService;
	
	@Autowired
	private IFileBackService fileBackService;
	
	@Autowired
	private IFileService fileService;


	// 文件根目录
	@Value("#{config.RootFilePath}")
	private String RootFilePath;
	
	//@RequestBody List<OAFileInfo> fileInfos
	/**
	 * 获取OA流程对应上传目录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/transferOAFile" })
	@ResponseBody
	public Map<String, Object> transferOAFile(@RequestBody List<FileInfoBack> fileInfos) {
		
		Map<String, Object> m = new HashMap<String, Object>();
		
		try {
			
			List<String> tempList = new ArrayList<String>();
			List<String> fileIds = new ArrayList<String>();	
			if(fileInfos!=null && fileInfos.size()>0) {
			  for(int i=0;i<fileInfos.size();i++){
				    FileInfoBack fileInfo = fileInfos.get(i);
				    //"code="+ fileInfo.getCode()+
				    System.out.println("fileName="+fileInfo.getFileName()+",oaId="+fileInfo.getOaId()+",type="+fileInfo.getType()+",requestId="+fileInfo.getRequestId()) ;  // 得到 每个对象中的属性值
				    tempList.add(fileInfo.getType());
				    fileIds.add(fileInfo.getOaId());
				  }
			}else {
				m.put("list", "");
				m.put("msg", "入参格式不正确");
				m.put("status", "2");
				return m;
			}
			
			if(fileIds != null && fileIds.size()>0) {
				fileService.deleteRecord(fileIds);
			}

			List<FilePath> filePaths = filePathService.getFilePath(tempList);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			List<FileInfo> fileList = new ArrayList<FileInfo>();
			
			List<OAFile> OAFileList = new ArrayList<OAFile>();
			
			if(filePaths!=null&&filePaths.size()>0) {
				for (int i = 0;i< filePaths.size();i++) {
					
					FilePath filePath = filePaths.get(i);
					for (int j = 0; j < fileInfos.size(); j++) {
						FileInfoBack fileInfo = fileInfos.get(j);
						if(fileInfo.getType().equals(filePath.getType())) {
							FileInfo cFileInfo = new FileInfo();
							cFileInfo.setOaId(fileInfo.getOaId());
							cFileInfo.setRequestId(fileInfo.getRequestId());
							cFileInfo.setType(fileInfo.getType());
							cFileInfo.setAuthor("OA");
							cFileInfo.setChannelId("1");
							cFileInfo.setFileName(fileInfo.getFileName());
							cFileInfo.setPath("/"+fileInfo.getCode()+filePath.getPath());
							cFileInfo.setUploadTime(new Date());
							cFileInfo.setFileSize(0);
							cFileInfo.setIsDeleted(0);
							fileList.add(cFileInfo);
							OAFile oAFile = new OAFile();
							oAFile.setOaId(fileInfo.getOaId());
							oAFile.setPath(RootFilePath+"/"+fileInfo.getCode()+filePath.getPath());
							OAFileList.add(oAFile);
						}
					}
					

				}
			}
			
/*			//删除原始数据
			if(fileList!= null && fileList.size()>0) {
				for (FileInfo file : fileList) {
					fileService.deleteFileInfo(file);
				}
			}*/

			
			//新增新的数据
			if(fileList!= null && fileList.size()>0) {
				for (FileInfo file : fileList) {
					fileService.saveFileInfo(file);
				}
			}

			m.put("list", OAFileList);
			m.put("msg", "查询数据成功");
			m.put("status", "1");
		} catch (Exception e) {
			m.put("list", "");
			m.put("msg", "查询数据失败");
			m.put("status", "0");
			e.printStackTrace();
			
		}

		return m;
	}

}
