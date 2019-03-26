package service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.IUserDao;
import pojo.Role;
import pojo.User;
import pojo.UserRole;
import pojo.UserTable;
import service.user.IUserService;
import vo.UserMenuVo;
import vo.UserTableVo;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	public List<UserTable> getUserByPhone(String userPhone) {

		// List<UserTable> userTableList = userDao.getUserByPhone(userPhone);
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUser(User user) {
		userDao.saveUser(user);
	}
	
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
	

	public List<UserTable> getUsers(UserTableVo obj) {

		return userDao.getUsers(obj);
	}

	@Override
	public List<UserMenuVo> getMenu(Integer userId, Integer status, Integer type, Integer parentName) {
		// TODO Auto-generated method stub
		return userDao.getMenu(userId, status, type, parentName);
	}

	@Override
	public List<User> getUsers(Integer userId, String userName, Integer status) {

		return userDao.getUser(userId, userName, status);
	}

	@Override
	public Role getRole(int userId) {

		List<Role> roleList = userDao.getRole(null, null, userId);

		if (roleList.size() > 0) {
			return roleList.get(0);
		}

		return null;
	}

	@Override
	public List<Role> getRoles(Integer roleId, int status) {
		// TODO Auto-generated method stub
		return userDao.getRoles(roleId, status);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUserRole(Integer userId, Integer roleId) {

		// 先删除当前用户的角色关系
		// userDao.deleteUserRole(userId);

		// 添加 用户角色
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);

		userDao.saveUserRole(userRole);

	}

	@Override
	public int deleteUserRole(Integer userId) {

		return userDao.deleteUserRole(userId);
	}

	@Override
	public boolean delUser(Integer userId) {
		userDao.delUser(userId);
		if (null == getUserById(userId)) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public void updateRole(Integer userId, Integer roleId) {
		userDao.updateRole(userId, roleId);
	}

	@Override
	public int getUserSq() {
		return userDao.getUserSq();
	}

	@Override
	public void modifyPwd(int userId, String newPwd) {
		userDao.modifyPwd(userId, newPwd);
	}

	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

}
