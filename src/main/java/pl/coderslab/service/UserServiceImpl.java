package pl.coderslab.service;

import java.util.List;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Role;
import pl.coderslab.entity.User;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	private final SessionRegistry sessionRegistry;

	private final UserRepo userRepository;
	private final RoleRepo roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepo userRepository, RoleRepo roleRepository, BCryptPasswordEncoder passwordEncoder, SessionRegistry sessionRegistry) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.sessionRegistry=sessionRegistry;
	}

	@Override
	public User findByUserName(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.setRole(userRole);
		userRepository.save(user);

	}


//	public void updateUser(User user) {
//		List<Object> loggedUsers = sessionRegistry.getAllPrincipals();
//		for (Object principal : loggedUsers) {
//			if (principal instanceof User) {
//				final User loggedUser = (User) principal;
//				if (user.getUsername().equals(loggedUser.getUsername())) {
//					List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
//					if (null != sessionsInfo && sessionsInfo.size() > 0) {
//						for (SessionInformation sessionInformation : sessionsInfo) {
//							sessionInformation.expireNow();
//							sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
//						}
//					}
//				}
//			}
//		}
//
//	}
}
