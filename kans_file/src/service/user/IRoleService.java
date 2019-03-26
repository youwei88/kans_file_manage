package service.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pojo.Operation;
import pojo.Role;

public interface IRoleService {

	/**
	 * 
	 * 增加一个角色
	 * 
	 * @param Role
	 * 
	 * @return
	 */
	public void addRole(Role role);
	
	
	/**
	 * 
	 * 修改角色
	 * 
	 * @param Role
	 * 
	 * @return
	 */
	public void updateRole(Role role);
	

	/**
	 * 
	 * 删除一个角色
	 * 
	 * @param Role
	 * 
	 * @return
	 */
	public void delRole(int id);

	/**
	 * 
	 * 查询所有行为
	 * 
	 * @param Role
	 * 
	 * @return
	 */
	public List<Operation> getAllOperations();

	/**
	 * 
	 * 查询一个角色对应的行为
	 * 
	 * @param Role
	 * 
	 * @return
	 */
	public List<Operation> getOperations(int id, int type);
	
	
	/**
	 * 
	 * 查询三级目录对应的行为
	 * 
	 * @param Role
	 * 
	 * @return
	 */
	public List<Operation> getOperationList(int id, int type, int isFromSap);

	/**
	 * 
	 * 增加一个权限
	 * 
	 * @param Operation
	 * 
	 * @return
	 */
	public void addOperation(Operation operation);

	/**
	 * 
	 * 删除一个权限
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public void delOperation(int id);
	
	/**
	 * 
	 * 根据权限id删除角色权限对应表
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public void delRoleOperationByOperId(int id);

	/**
	 * 
	 * 删除一个角色的权限
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public void delRoleOperation(int roleid);

	/**
	 * 
	 * 增加一个角色的权限
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public void addRoleOperation(int roleid, int operId);

	/**
	 * 
	 * 查询所有目录
	 * 
	 * @param
	 * 
	 * @return
	 */
	public List<Operation> getAllDirs();

	/**
	 * 
	 * 根据id查询父目录名
	 * 
	 * @param
	 * 
	 * @return
	 */
	public String getParentName(int id);

	/**
	 * 
	 * 修改一个权限
	 * 
	 * @param Operation
	 * 
	 * @return
	 */
	public void updateOperation(Operation operation);
	
	
	public List<Operation> getMaterialOperate(int userId, int menuId);

}