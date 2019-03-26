package service.file;

import java.util.List;

import pojo.FileInfo;
import pojo.Operation;

public interface IFileService {

	/**
	 * 存储文件信息
	 */
	public void saveFileInfo(FileInfo file);

	/**
	 * 根据名字获取文件信息
	 */
	public FileInfo getFileInfo(String fileName, String path);
	
	/***
	 * 获取OA上传的文件信息
	 * @param requestId  流程ID
	 * @param oaId 附件ID
	 * @param type 文件类型编码
	 * @return
	 */
	public FileInfo getFileInfoById(String requestId, String oaId,String type);

	/**
	 * 删除文件信息
	 */
	public void delFileInfo(int id);

	/**
	 * 更新文件信息
	 */
	public void updateFileInfo(FileInfo file);
	
	/**
	 * 删除文件信息
	 */
	public void deleteFileInfo(FileInfo file);

	/**
	 * 添加目录
	 */
	public void addDir(Operation op);

	/**
	 * 应用到目录
	 */
	public void applyDir(String path);
	
	/**
	 * 逻辑删除
	 */
	public void delFileMark(int id);
	
	/**
	 * 获得user_operation表的下一个主键值
	 */
	public int getOperNext();

	/**
	 * 根据operName获得主键id
	 */
	public Integer getIdByOperName(String operName);
	
	/**
	 * 删除表中记录
	 * @param fileIds
	 */
	public void deleteRecord(List<String> fileIds);
	
	
	/***
	 * 获得文件上传路径
	 * @param requestId  流程ID
	 * @param oaId 附件ID
	 * @param type 文件类型编码
	 * @return
	 */
	public FileInfo getFileById(Integer id);
	
	/***
	 * 获得物料管理下拉类型
	 * @return
	 */
	public List<Operation> getOption();
	
	/***
	 * 通过code获得当前对象的ID
	 * @return
	 */
	public Operation getInfoBycode(String code);
}