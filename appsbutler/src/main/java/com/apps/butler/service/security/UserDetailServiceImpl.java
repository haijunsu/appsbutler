package com.apps.butler.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import com.apps.butler.entity.user.Authority;
import com.apps.butler.entity.user.Role;
import com.apps.butler.entity.user.User;
import com.apps.butler.service.user.UserManager;

/**
 * 实现SpringSecurity的UserDetailsService接口,获取用户Detail信息.
 * 
 * @author calvin
 */
public class UserDetailServiceImpl implements UserDetailsService {

	private UserManager userManager;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		User user = userManager.getUserByLoginName(userName);
		if (user == null)
			throw new UsernameNotFoundException(userName + " 不存在");

		List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuths()) {
				authsList.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}

		// 目前在appsbutler的User类中没有enabled, accountNonExpired,credentialsNonExpired, accountNonLocked等属性
		// 暂时全部设为true,在需要时才添加这些属性.
		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				user.getLoginName(), user.getPassword(), true, true, true, true, authsList
						.toArray(new GrantedAuthority[authsList.size()]));

		return userdetail;
	}

	@Required
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
