package service.file.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.IFilePathDao;
import dao.IRoleDao;
import pojo.FilePath;
import pojo.Role;
import service.file.IOAFileService;

@Service
public class OAFileServiceImpl implements IOAFileService {
	
	protected static Logger logger = Logger.getLogger(IFilePathServiceImpl.class);

	@Autowired
	IFilePathDao iFilePathDao;

	@Override
	public List<FilePath> getList() {
		return iFilePathDao.getList();
	}

	@Override
	public void addFilePath(FilePath filePath) {
		iFilePathDao.addFilePath(filePath);
	}

	@Override
	public void updateFilePath(FilePath filePath) {
		iFilePathDao.updateFilePath(filePath);
	}

	@Override
	public void delFilePath(int id) {
		iFilePathDao.delFilePath(id);
	}

}
