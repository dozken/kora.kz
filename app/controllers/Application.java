package controllers;

import models.user.AuthorisedUser;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.common.*;

public class Application extends Controller {

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");

		return ok(Routes.javascriptRouter("myJsRoutes",
				controllers.routes.javascript.Application.index(),
				controllers.routes.javascript.User.register()
				));
	}

	// public static Result changeLanguage(String language) {
	// Controller.changeLang(language);
	// return redirect(request().getHeader("referer"));
	// }

	public static Result signIn() {

		AuthorisedUser user = AuthorisedUser
				.findByEmail("info@technovision.kz");
		session("connected", user.email);
		flash("thank you");
		return redirect(request().getHeader("referer"));
	}

	public static Result signOut() {
		session().clear();
		flash("thank you");
		return redirect(routes.Application.index());
	}

	public static Result signUp() {
		return redirect(request().getHeader("referer"));
	}

	public static Result index() {
		return ok(views.html.common.index.render());
	}

	public static Result about() {
		return ok(about.render());
	}

	public static Result feedback() {
		return ok(feedback.render());
	}
	
}
