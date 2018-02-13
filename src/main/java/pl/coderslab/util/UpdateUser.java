package pl.coderslab.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import pl.coderslab.entity.User;

public class UpdateUser {

//	@Autowired
//	private SessionRegistry sessionRegistry;
//
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
