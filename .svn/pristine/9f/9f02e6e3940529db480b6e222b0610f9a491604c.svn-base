package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pojo.FilePath;

public interface IFilePathDao {

	List<FilePath> getFilePath(@Param("types")List<String> types);

	List<FilePath> getList();

	void addFilePath(FilePath filePath);

	void updateFilePath(FilePath filePath);

	void delFilePath(int id);

}
