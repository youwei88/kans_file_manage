package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dao.IUserDao;
import dao.UserDao;
import domain.DbUser;
import vo.UserVo;

/**
 * �?��自定义的service用来和数据库进行操作. 即以后我们要通过数据库保存权�?则需要我们继承UserDetailsService
 * 
 * @author liukai
 * 
 */
public class CustomUserDetailsService implements UserDetailsService {

	protected static Logger logger = Logger.getLogger("service");

//	private UserDao userDAO = new UserDao();
	
	@Autowired
	private IUserDao dao;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {

		UserVo user = null;

		try {

			// 搜索数据库以匹配用户登录
//			DbUser dbUser = userDAO.getDatabase(username);
			
			List<pojo.User> userList = dao.getUser(null,userName,null);
			pojo.User u = null;
			
			//判断数据
			if(userList.size() == 1){
				u = userList.get(0);
			}
			
			if(u!=null){
				user = new UserVo(u.getUserName(), u.getUserPwd()
						.toLowerCase(), true, true, true, true,
						getAuthorities(u.getStatus()));
				
				user.setId(u.getId());
				user.setStatus(u.getStatus());
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in retrieving user");
			throw new UsernameNotFoundException("Error in retrieving user");
		}

		return user;
	}

	/**
	 * 获得访问角色权限
	 * 
	 * @param access
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(Integer access) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

		
		// �?��的用户默认拥有ROLE_USER权限
		logger.debug("Grant ROLE_USER to this user");
		authList.add(new GrantedAuthorityImpl("ROLE_USER"));
		

		// 如果参数access�?.则拥有ROLE_ADMIN权限
		if (access.compareTo(1) == 0) {
			logger.debug("Grant ROLE_ADMIN to this user");
			authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}

		return authList;
	}
}
