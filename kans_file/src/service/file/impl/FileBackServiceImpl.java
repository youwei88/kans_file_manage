package service.file.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.IFileBackDao;
import pojo.FileInfoBack;
import service.file.IFileBackService;

@Service
public class FileBackServiceImpl implements IFileBackService {

	@Autowired
	IFileBackDao iFileBackDao;
	
	@Override
	public void saveFileBack(FileInfoBack file) {
		iFileBackDao.saveFileBack(file);
	}

	@Override
	public void deleteRecord(List<String> fileIds) {
		iFileBackDao.deleteRecord(fileIds);
		
	}

}
