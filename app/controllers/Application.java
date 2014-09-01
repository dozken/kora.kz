package controllers;

import static play.data.Form.form;
import models.user.AuthorisedUser;
import play.Routes;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.about;
import views.html.common.feedback;

public class Application extends Controller {

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");

		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.Application.index(),
				controllers.routes.javascript.Application.signIn(),
				controllers.routes.javascript.User.register(),
				controllers.routes.javascript.Filters.getBreeds(),
				controllers.routes.javascript.Filters.addBreed(),
				controllers.routes.javascript.Filters.updateBreed(),
				controllers.routes.javascript.Filters.deleteBreed(),
				controllers.routes.javascript.Filters.addRegion(),
				controllers.routes.javascript.Filters.updateRegion(),
				controllers.routes.javascript.Filters.deleteRegion(),
				controllers.routes.javascript.Settings.changePassword(),
				controllers.routes.javascript.Settings.changeUserSetting(),
				controllers.routes.javascript.Settings.changeAdminSetting(),
				controllers.routes.javascript.Advertisements.add(),
				controllers.routes.javascript.Advertisements.remove(),
				controllers.routes.javascript.Advertisements.replace()
				
		));
	}
	

	// public static Result changeLanguage(String language) {
	// Controller.changeLang(language);
	// return redirect(request().getHeader("referer"));
	// }

	public static Result signIn() {
		DynamicForm requestData = form().bindFromRequest();
		AuthorisedUser user = AuthorisedUser.findByEmail(requestData
				.get("email"));
		if (user != null && user.password.equals(requestData.get("password"))) {
			session("connected", user.email);
			flash("thank you");
			return redirect(request().getHeader("referer"));
		} else {
			return ok("error");

		}
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
