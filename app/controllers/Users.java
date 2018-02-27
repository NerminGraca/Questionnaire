package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import daos.service.AuthenticationService;
import daos.service.PasswordResetService;
import daos.service.QuestionnaireService;
import daos.service.UserQuestionnaireService;
import daos.service.UserService;
import helpers.EmailService;
import models.PasswordReset;
import models.Questionnaire;
import models.User;
import models.UserQuestionnaire;
import models.User.EmailReset;
import models.User.Login;
import models.User.NewPassword;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Context;
import views.html.*;
import views.html.questionnaire.questionnaires;
import wrappers.PersistenceServiceException;

import static helpers.SessionHelper.AUTHENTICATED_USER;

public class Users extends Controller {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(Users.class);
	private final UserService userService = new UserService();
	private final AuthenticationService apiKeyService = new AuthenticationService();
	private final PasswordResetService passwordResetService = new PasswordResetService();
	private final QuestionnaireService questionnaireService = new QuestionnaireService();
	private final UserQuestionnaireService userQuestionnaireService = new UserQuestionnaireService();

	@Inject Configuration cfg;
	@Inject FormFactory formFactory;
	@Inject EmailService emailService;
	
	public Result register() {
		return ok(register.render(new Form<User>(User.class, null, null, null)));
	}

	public Result login() {
		System.out.println("login");
		return ok(login.render(new Form<Login>(Login.class, null, null, null)));
	}
	
	public Result submitRegistration() {
		Form<User> userForm = formFactory.form(User.class);
		try {
			Form<User> form = userForm.bindFromRequest();
			if (form.hasErrors() || form.hasGlobalErrors()) {
				return ok(register.render(form));
			} else {
				User user = form.get();
				if (userService.isExistingUser(user.getEmail())) {
					List<ValidationError> validationErrorList = new ArrayList<>();
					validationErrorList.add(new ValidationError("email", "This e-mail is already registered."));
					form.errors().put("email", validationErrorList);
					return ok(register.render(form));
				}
				if (!user.isPasswordMatched()) { // check is password match
					List<ValidationError> validationErrorList = new ArrayList<>();
					validationErrorList.add(new ValidationError("passwordConfirmation", "Password must confirm"));
					form.errors().put("passwordConfirmation", validationErrorList);
					return ok(register.render(form));
				} else {
					if (user.getEmail() != null) {
						String email = user.getEmail().toLowerCase();
						user.setEmail(email);
					}
					user.setPasswordDigest(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
					userService.saveUser(user);
					Context.current().session().put(AUTHENTICATED_USER, user.getId().toString());
					session("token", apiKeyService.getOrGenerateAccessToken(user).getToken());

					return redirect(controllers.routes.Questionnaires.questionnaires());
				}
			}
		} catch (PersistenceServiceException e) {
			LOGGER.error("Users.saveUser failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
	}
	
	public Result submitLogin() {
		try {
			Form<Login> form = formFactory.form(Login.class).bindFromRequest();
			if (form.hasErrors() || form.hasGlobalErrors()) {
				return ok(login.render(form));
			}
			Login loginData = form.get();
			String email = loginData.getEmail();
			String password = loginData.getPassword();
			User user = apiKeyService.getAuthenticatedUser(email, password);
			if (user == null) {
				List<ValidationError> validationErrorList = new ArrayList<>();
				validationErrorList.add(new ValidationError("password", "Invalid user or password"));
				form.errors().put("password", validationErrorList);
				return ok(login.render(form));
			}
			if (userService.isExistingUser(email)) {
				Context.current().session().put(AUTHENTICATED_USER, user.getId().toString());
				session("token", apiKeyService.getOrGenerateAccessToken(user).getToken());

				return redirect(controllers.routes.Questionnaires.questionnaires());
			}
			return ok(login.render(form));
		} catch (PersistenceServiceException e) {
			LOGGER.error("Users.login failed ", e);
			return ok(errorpage.render(e.getMessage()));
		}
	}
	
	public Result fbLogin(String fbId, String fbUsername) {
		try {
			User user = userService.getUserByFacebookID(fbId);
			if (user != null) {
				
				Context.current().session().put(AUTHENTICATED_USER, user.getId().toString());
				session("token", apiKeyService.getOrGenerateAccessToken(user).getToken());

				List<Questionnaire> qList = questionnaireService.getAllQuestionnaires();
				List<UserQuestionnaire> uqList = userQuestionnaireService.findByUser(user.getId());
				
				for (UserQuestionnaire uq : uqList) {
					qList.remove(uq.getQuestionnaire());
				}
				
				return ok(questionnaires.render(user, qList));
			} else {
				
				user = new User();
				user.setFacebookId(fbId);
				user.setFacebookUserame(fbUsername);
				userService.saveUser(user);
				
				Context.current().session().put(AUTHENTICATED_USER, user.getId().toString());
				session("token", apiKeyService.getOrGenerateAccessToken(user).getToken());

				List<Questionnaire> qList = questionnaireService.getAllQuestionnaires();
				List<UserQuestionnaire> uqList = userQuestionnaireService.findByUser(user.getId());
				
				for (UserQuestionnaire uq : uqList) {
					qList.remove(uq.getQuestionnaire());
				}
				
				return ok(questionnaires.render(user, qList));
			}
		} catch (PersistenceServiceException e) {
			LOGGER.error("Users.fbLogin failed ", e);
			return ok(errorpage.render(e.getMessage()));
		}
	}
	
	public Result resetPassword() {
		return ok(resetPassword.render(new Form<EmailReset>(EmailReset.class, null, null, null)));
	}
	
	public Result submitResetPassword() {
		
		Form<EmailReset> form = formFactory.form(EmailReset.class).bindFromRequest();
		if (form.hasErrors() || form.hasGlobalErrors()) {
			return ok(resetPassword.render(form));
		}
		EmailReset emailReset = form.get();
		String email = emailReset.getEmail();
		
		PasswordReset pr = new PasswordReset();
		pr.setEmail(email);
		String token;
		try {
			token = passwordResetService.savePasswordReset(pr);
			emailService.sendEmail(email, cfg.getString("host.url") + "/newPassword?token=" + token);
			
			return ok(passwordReseted.render());
		} catch (PersistenceServiceException e) {
			LOGGER.error("Users.submitResetPassword failed ", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}
	
	public Result newPassword(String token) {
		
		try {
			PasswordReset passwordResetByToken = passwordResetService.getPasswordResetByToken(token);
			if (passwordResetByToken != null && !passwordResetByToken.isTokenExpired()) {
				
				return ok(newPassword.render(new Form<NewPassword>(NewPassword.class, null, null, null), token));
				
			}
			return ok(errorpage.render("Request invalid"));

		} catch (PersistenceServiceException e) {
			LOGGER.error("Users.submitResetPassword failed ", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}
	
	public Result submitNewPassword(String token) {
		
		Form<NewPassword> form = formFactory.form(NewPassword.class).bindFromRequest();
		if (form.hasErrors() || form.hasGlobalErrors()) {
			return ok(newPassword.render(form, token));
		}
		
		NewPassword newPasswordObj = form.get();
		if (!newPasswordObj.isPasswordMatched()) {
			List<ValidationError> validationErrorList = new ArrayList<>();
			validationErrorList.add(new ValidationError("passwordConfirmation", "Password must confirm"));
			form.errors().put("passwordConfirmation", validationErrorList);
			return ok(newPassword.render(form, token));
		}
		
		// SAVE ACTUAL NEW PASSWORD
		try {
			PasswordReset passwordReset = passwordResetService.getPasswordResetByToken(token);
			if (!passwordReset.isTokenExpired()) {
				
				String email = passwordReset.getEmail();
				User user = userService.getUserByEmail(email);
				user.setPasswordDigest(BCrypt.hashpw(newPasswordObj.getPassword(), BCrypt.gensalt()));
				userService.saveUser(user);
				
				return ok(login.render(new Form<Login>(Login.class, null, null, null)));
			}
			
			return ok(errorpage.render("Request invalid"));
		} catch (PersistenceServiceException e) {
			LOGGER.error("Users.submitNewPassword failed ", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}
	
	public Result privacyPolicy() {
		return ok(privacyPolicy.render());
	}
	
	public Result logout() {
		session().clear();
		return ok(login.render(new Form<Login>(Login.class, null, null, null)));
	}

}
