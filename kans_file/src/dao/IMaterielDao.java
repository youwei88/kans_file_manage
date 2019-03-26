package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pojo.Materiel;
import pojo.Operation;

public interface IMaterielDao {

	public List<Materiel> getMateriels(@Param("code") String code, @Param("dscp") String dscp , @Param("isUserCreated") String isUserCreated);

	public void synchronizeMateriels(@Param("m") Materiel m);

	public void delMateriels();

	public void delMaterielById(String code);
	
	public String selectDescBycode(String code);

	// 物理删除
	public void delMaterielSync(String code);

	public List<Operation> findSonDir(@Param("parId") int parId, @Param("title") String title);

	public int findFirDir(String code);
	
	public String getMaxOrderNum();
	
	public List<Materiel> queryMaterielBatch(@Param("codes")List<String> codes);
	
}
