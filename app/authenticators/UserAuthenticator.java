package authenticators;

import static helpers.SessionHelper.AUTHENTICATED_USER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import daos.service.AuthenticationService;
import models.User;
import models.User.Login;
import play.data.Form;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import views.html.login;
import wrappers.PersistenceServiceException;

public class UserAuthenticator extends Security.Authenticator {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserAuthenticator.class);
	
	private final AuthenticationService authenticationService = new AuthenticationService();
	
	public String getUsername(Context ctx) {
		String token = ctx.session().get("token");
		String email = null;
		if (token != null) {
			User authUser = null;
			try {
				authUser = authenticationService.getAuthenticatedUser(token);
				if (authUser != null) {
					Context.current().session().put(AUTHENTICATED_USER, authUser.getId().toString());
					email = authUser.getEmail();
					if (email == null) {
						email = authUser.getFacebookId();
					}
				}
			} catch (PersistenceServiceException e) {
				LOGGER.error("Error with user authentication", e);
			}
		}
		return email;
	}
	
	public Result onUnauthorized(Context ctx) {
		ctx.session().clear();
		return ok(login.render(new Form<Login>(Login.class, null, null, null)));
	}

}
