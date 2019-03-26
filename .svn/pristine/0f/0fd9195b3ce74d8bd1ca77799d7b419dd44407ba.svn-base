package service.file.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.IFilePathDao;
import pojo.FilePath;
import pojo.OAFileInfo;
import service.file.IFilePathService;

@Service
public class IFilePathServiceImpl implements IFilePathService {


	protected static Logger logger = Logger.getLogger(IFilePathServiceImpl.class);

	@Autowired
	IFilePathDao iFilePathDao;

	@Override
	public List<FilePath> getFilePath(List<String> types) {
		return iFilePathDao.getFilePath(types);
	}

}
