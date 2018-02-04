package pl.coderslab.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.coderslab.entity.Role;
import pl.coderslab.entity.User;

public class SpringDataUserDetailsService implements UserDetailsService {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userService.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Role role = user.getRole();
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		for (Role role : user.getRole()) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}
