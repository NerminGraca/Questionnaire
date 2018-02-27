package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import daos.service.UserService;
import models.User;
import play.mvc.Http.Context;
import wrappers.PersistenceServiceException;

public final class SessionHelper {
	
	public final static String AUTHENTICATED_USER = "AUTHENTICATED_USER";
	private final static UserService userService = new UserService();
	private final static Logger LOGGER = LoggerFactory.getLogger(SessionHelper.class);
	
	private SessionHelper() {}
	
	public static User getAuthenticatedUser() {
		
		User user = null;
		String userId = Context.current().session().get("AUTHENTICATED_USER");
		if (userId != null) {
			try {
				user = userService.getUserById(Integer.valueOf(userId));
			} catch (NumberFormatException e) {
				LOGGER.error("SessionHelper.getAuthenticatedUser failed", e);
			} catch (PersistenceServiceException e) {
				LOGGER.error("SessionHelper.getAuthenticatedUser failed", e);
			}
		}
		
		return user;
		
	}

}
