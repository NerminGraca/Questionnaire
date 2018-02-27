package controllers;

import authenticators.UserAuthenticator;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;

public class JavascriptController extends Controller {

	@Security.Authenticated(UserAuthenticator.class)
	public Result javascriptRoutes() {
		return ok(
				JavaScriptReverseRouter.create("jsRoutes", 
						
						routes.javascript.Questionnaires.appendQuestion()
				)
						
		).as("text/javascript");
	}

}
