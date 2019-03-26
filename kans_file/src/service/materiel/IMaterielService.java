package service.materiel;

import java.util.List;
import java.util.Map;

import pojo.Materiel;
import pojo.Operation;

public interface IMaterielService {
	/**
	 * 获取全部物料编码
	 */
	public List<Materiel> getMateriels(String code, String dscp ,String isusercreated);

	/**
	 * 同步物料编码
	 */
	public void synchronizeMateriels(String start, String end);

	/**
	 * 新增物料编码
	 */
	public void synchronizeMateriels(Materiel m);
	
	/**
	 * 根据物料号查看物料描述
	 */
	public String selectDescBycode(String code);

	/**
	 * 删除物料
	 */
	public void delMaterielById(String code);

	/**
	 * 物理删除
	 */
	public void delMaterielSync(String code);

	/**
	 * 根据父目录找到子目录
	 */
	public List<Operation> findSonDir(int id, String code);
	
	/**
	 * 获得最大排序号
	 * @return
	 */
	public String getMaxOrderNum();
	
	/**
	 * 批量获取匹配单号
	 * @return
	 */
	public List<Materiel> queryMaterielBatch(List<String> codes);
	
	
}
